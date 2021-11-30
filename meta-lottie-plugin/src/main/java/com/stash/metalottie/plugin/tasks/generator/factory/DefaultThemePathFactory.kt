package com.stash.metalottie.plugin.tasks.generator.factory

import com.stash.metalottie.plugin.tasks.generator.model.LottieThemePath
import com.stash.metalottie.plugin.tasks.generator.model.LottieValue
import com.stash.metalottie.plugin.tasks.generator.model.theme.ThemeMapper
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.double
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.math.pow
import kotlin.math.round

/**
 * root
 * -> assets
 *   -> layers (below)
 * -> layers
 *   -> sc
 *   -> shapes
 *     -> gr
 *       -> it
 *         -> [shape loop]
 *     -> st
 *       -> c
 *         -> k [r,g,b,a]
 *     -> gs
 *       -> g
 *         -> p (points - number of colors)
 *         -> k [position,r,g,b,position,opacity]
 *     -> fl
 *       -> c
 *         -> k [r,g,b,a]
 *     -> gf
 *       -> g
 *         -> p (points - number of colors)
 *         -> k [position,r,g,b,position,opacity]
 *   -> t
 *     -> d
 *       -> fc [r,g,b]
 *       -> sc [r,g,b]
 *     -> a
 *       -> fc [r,g,b,a]
 *       -> sc [r,g,b,a]
 */

class DefaultThemePathFactory : ThemePathFactory {
    companion object {
        /**
         * Decimal place helper
         * (x * 10^n) / 10^n ==> x.{n} decimal places
         */
        val DECIMAL_PLACE_MULTIPLIER = 10.0.pow(8)

        const val MAX_COLOR_VALUE = 255.0

        /**
         * Top Level
         */
        const val LOTTIE_FIELD_ASSETS = "assets"
        const val LOTTIE_FIELD_LAYERS = "layers"
        const val LOTTIE_FIELD_SHAPES = "shapes"

        /**
         * GENERIC
         */
        const val LOTTIE_FIELD_ID = "id"
        const val LOTTIE_FIELD_NAME = "nm"
        const val LOTTIE_FIELD_REF_ID = "refId"
        const val LOTTIE_FIELD_SOLID_COLOR = "sc"
        const val LOTTIE_FIELD_TYPE = "ty"

        /**
         * Shape
         */
        const val LOTTIE_SHAPE_TYPE_SHAPE_GROUP = "gr"
        const val LOTTIE_SHAPE_TYPE_STROKE = "st"
        const val LOTTIE_SHAPE_TYPE_FILL = "fl"

        const val LOTTIE_SHAPE_COLOR = "c"
        const val LOTTIE_SHAPE_COLOR_KEYFRAMES = "k"
        const val LOTTIE_SHAPE_GROUP_ITERABLE = "it"
    }

    override fun create(lottieJson: String, themeMapper: ThemeMapper): List<LottieThemePath> {
        val rootElement = Json.parseToJsonElement(lottieJson)
        val assetPreCompLayerList: Map<String, List<List<LottieValue>>> = getAssetPaths(rootElement)
        val layerList: List<List<LottieValue>> = getLayerPaths(rootElement, assetPreCompLayerList)

        return layerList.mapNotNull {
            val kp = it.filterIsInstance<LottieValue.Layer>().map { value -> value.key }
            val colorValue = (it.last() as LottieValue.ColorValue)

            themeMapper.getThemeToken(colorValue.property, colorValue.color)?.let { themeKey ->
                LottieThemePath(
                    kp,
                    colorValue.property,
                    themeKey,
                    colorValue.color
                )
            }
        }
    }

    private fun getAssetPaths(rootElement: JsonElement): Map<String, List<List<LottieValue>>> {
        // Loop through asset objects (expected -> string to list<keyPath>)
        val assetLayers = rootElement.jsonObject[LOTTIE_FIELD_ASSETS]?.jsonArray?.map { asset ->
            val id = asset.jsonObject[LOTTIE_FIELD_ID]!!.jsonPrimitive.content
            val layers = asset.jsonObject[LOTTIE_FIELD_LAYERS]?.jsonArray?.flatMap {
                processLayer(it.jsonObject)
            } ?: listOf()

            Pair(id, layers)
        }?.toMap()

        return assetLayers ?: mapOf()
    }

    private fun getLayerPaths(
        rootElement: JsonElement,
        assetPreCompLayerList: Map<String, List<List<LottieValue>>>
    ): List<List<LottieValue>> {
        return rootElement.jsonObject[LOTTIE_FIELD_LAYERS]?.jsonArray?.flatMap { layer ->
            val layObj = layer.jsonObject
            val layers = processLayer(layObj)

            if (layer.jsonObject.containsKey(LOTTIE_FIELD_REF_ID)) {
                val preCompLayers =
                    assetPreCompLayerList[layObj[LOTTIE_FIELD_REF_ID]!!.jsonPrimitive.content]!!.map {
                        listOf(LottieValue.Layer(layObj[LOTTIE_FIELD_NAME]!!.jsonPrimitive.content)) + it
                    }
                layers + preCompLayers
            } else {
                layers
            }
        } ?: listOf()
    }

    private fun processLayer(layer: JsonObject): List<List<LottieValue>> {
        val layerName = LottieValue.Layer(
            layer[LOTTIE_FIELD_NAME]!!.jsonPrimitive.content,
            layer[LOTTIE_FIELD_REF_ID]?.jsonPrimitive?.content
        )
        val layerKeyLists: MutableList<List<LottieValue>> = mutableListOf()

        if (layer.containsKey(LOTTIE_FIELD_SOLID_COLOR)) {
            layerKeyLists.add(
                listOf(
                    layerName,
                    LottieValue.ColorValue(
                        "SOLID_COLOR",
                        layer[LOTTIE_FIELD_SOLID_COLOR]!!.jsonPrimitive.content
                    )
                )
            )
        }

        layer[LOTTIE_FIELD_SHAPES]?.jsonArray?.forEach { shape ->
            processShape(shape.jsonObject).takeIf { it.isNotEmpty() }?.let {
                layerKeyLists.add(listOf(layerName) + it)
            }
        }

        return layerKeyLists
    }

    private fun processShape(shape: JsonObject): List<LottieValue> {
        val shapeName = LottieValue.Layer(shape[LOTTIE_FIELD_NAME]!!.jsonPrimitive.content)
        val type = shape[LOTTIE_FIELD_TYPE]?.jsonPrimitive?.content

        return when (type) {
            LOTTIE_SHAPE_TYPE_STROKE -> {
                processColor(shape[LOTTIE_SHAPE_COLOR]!!.jsonObject)?.let {
                    listOf(shapeName, LottieValue.ColorValue("STROKE_COLOR", it))
                } ?: listOf()
            }
            LOTTIE_SHAPE_TYPE_FILL -> {
                processColor(shape[LOTTIE_SHAPE_COLOR]!!.jsonObject)?.let {
                    listOf(shapeName, LottieValue.ColorValue("FILL_COLOR", it))
                } ?: listOf()
            }
            LOTTIE_SHAPE_TYPE_SHAPE_GROUP -> {
                shape[LOTTIE_SHAPE_GROUP_ITERABLE]!!.jsonArray.flatMap {
                    processShape(it.jsonObject)
                }.takeIf { it.isNotEmpty() }?.let { listOf(shapeName) + it } ?: listOf()
            }
            else -> listOf()
        }
    }

    /**
     * Color processing here ignores alpha. May look at including in the meta data in the future
     */
    private fun processColor(colorObject: JsonObject): String? {
        try {
            val kValues = colorObject[LOTTIE_SHAPE_COLOR_KEYFRAMES]!!.jsonArray

            var r: Double =
                round(kValues[0].jsonPrimitive.double * DECIMAL_PLACE_MULTIPLIER) / DECIMAL_PLACE_MULTIPLIER
            var g: Double =
                round(kValues[1].jsonPrimitive.double * DECIMAL_PLACE_MULTIPLIER) / DECIMAL_PLACE_MULTIPLIER
            var b: Double =
                round(kValues[2].jsonPrimitive.double * DECIMAL_PLACE_MULTIPLIER) / DECIMAL_PLACE_MULTIPLIER

            if (r <= 1 && g <= 1 && b <= 1) {
                r = round(r * MAX_COLOR_VALUE)
                g = round(g * MAX_COLOR_VALUE)
                b = round(b * MAX_COLOR_VALUE)
            }

            val result = (r.toInt() shl 16) or (g.toInt() shl 8) or b.toInt()
            return String.format("%06X", 0xFFFFFF and result)
        } catch (e: Exception) {
            println("Issue detected within lottie file: $colorObject")
            return null
        }
    }
}

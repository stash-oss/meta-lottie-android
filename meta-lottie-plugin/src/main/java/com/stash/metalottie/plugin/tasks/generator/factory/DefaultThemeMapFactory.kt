package com.stash.metalottie.plugin.tasks.generator.factory

import com.stash.metalottie.plugin.tasks.generator.model.LottieThemeProperty
import com.stash.metalottie.plugin.tasks.generator.model.theme.LottieInputThemeMap
import com.stash.metalottie.plugin.tasks.generator.model.theme.LottieInputThemeMapItem
import com.stash.metalottie.plugin.tasks.generator.model.theme.ThemeMapper
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class DefaultThemeMapFactory(themeMapJson: String) : ThemeMapper {
    private val sourceMap: Map<String, Map<String, String>>

    init {
        val (strokeColors, fillColors) = Json.decodeFromString<LottieInputThemeMap>(themeMapJson)
        sourceMap = mapOf(
            LottieThemeProperty.STROKE_COLOR.keyName to transformThemeMap(strokeColors),
            LottieThemeProperty.FILL_COLOR.keyName to transformThemeMap(fillColors)
        )
    }

    override fun getThemeToken(property: String, colorValue: String): String? {
        return sourceMap[property]?.get(colorValue)
    }

    private fun transformThemeMap(values: List<LottieInputThemeMapItem>): Map<String, String> {
        return values.map {
            it.color to it.name
        }.toMap()
    }
}

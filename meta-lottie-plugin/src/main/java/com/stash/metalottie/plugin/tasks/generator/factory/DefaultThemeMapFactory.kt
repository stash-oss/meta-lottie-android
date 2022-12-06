package com.stash.metalottie.plugin.tasks.generator.factory

import com.stash.metalottie.plugin.tasks.generator.model.LottieThemeProperty
import com.stash.metalottie.plugin.tasks.generator.model.theme.LottieInputThemeMap
import com.stash.metalottie.plugin.tasks.generator.model.theme.LottieInputThemeMapItem
import com.stash.metalottie.plugin.tasks.generator.model.theme.ThemeMapper

class DefaultThemeMapFactory(themeMap: LottieInputThemeMap) : ThemeMapper {
    private val sourceMap: Map<String, Map<String, String>>

    init {
        sourceMap = mapOf(
            LottieThemeProperty.STROKE_COLOR.keyName to transformThemeMap(themeMap.strokeColors),
            LottieThemeProperty.FILL_COLOR.keyName to transformThemeMap(themeMap.fillColors)
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

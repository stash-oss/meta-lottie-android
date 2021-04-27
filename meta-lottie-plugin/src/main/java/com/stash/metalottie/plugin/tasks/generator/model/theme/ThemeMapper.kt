package com.stash.metalottie.plugin.tasks.generator.model.theme

interface ThemeMapper {

    /**
     * Retrieve theme token from hex color value
     *
     * @param property value property type (STROKE_COLOR, FILL_COLOR)
     * @param colorValue encoded property color value from lottie file
     * @return theme token if match  is found or null if no theme mapping found
     */
    fun getThemeToken(property: String, colorValue: String): String?
}

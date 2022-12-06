package com.stash.metalottie.plugin.tasks.generator.parser

import com.stash.metalottie.plugin.tasks.generator.model.theme.LottieInputThemeMap
import java.io.File
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object ThemeParser {
    /**
     * Parses a theme definition file to produce a MetaLottieTheme
     *
     * Theme definition Example:
     * {
     *   "strokeColors": [
     *     {
     *       "name": "iconPrimary",
     *       "color": "0B1620"
     *     }
     *   ],
     *   "fillColors": [
     *     {
     *       "name": "bgPrimary",
     *       "color": "FFFFFF"
     *     }
     *   ]
     * }
     */
    fun parse(
        themeFile: File
    ): LottieInputThemeMap {
        val themeMapJson = themeFile.readText()

        return Json.decodeFromString(themeMapJson)
    }
}

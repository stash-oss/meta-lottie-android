package com.stash.metalottie.plugin.tasks.generator.parser

import com.stash.metalottie.plugin.tasks.generator.factory.DefaultThemeMapFactory
import com.stash.metalottie.plugin.tasks.generator.factory.DefaultThemePathFactory
import com.stash.metalottie.plugin.tasks.generator.factory.ThemePathFactory
import com.stash.metalottie.plugin.tasks.generator.model.LottieMetadata
import com.stash.metalottie.plugin.tasks.generator.model.LottieThemePath
import java.io.File

object MetadataParser {
    /**
     * Parses a raw lottie file and a theme definition file to produce a LottieMetadata instance
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
        lottieFile: File,
        themeFile: File,
        themePathFactory: ThemePathFactory = DefaultThemePathFactory()
    ): LottieMetadata {
        val lottieJson = lottieFile.readText()

        val themeMapper = DefaultThemeMapFactory(ThemeParser.parse(themeFile))
        val themePaths: List<LottieThemePath> = themePathFactory.create(lottieJson, themeMapper)

        return LottieMetadata(themePaths = themePaths)
    }
}

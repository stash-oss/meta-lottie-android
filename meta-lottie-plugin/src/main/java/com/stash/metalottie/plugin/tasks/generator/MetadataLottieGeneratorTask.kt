package com.stash.metalottie.plugin.tasks.generator

import com.stash.metalottie.plugin.tasks.generator.factory.DefaultThemePathFactory
import com.stash.metalottie.plugin.tasks.generator.factory.ThemePathFactory
import com.stash.metalottie.plugin.tasks.generator.model.LottieMetadata
import com.stash.metalottie.plugin.tasks.generator.model.LottieThemePath
import com.stash.metalottie.plugin.tasks.generator.model.ThemeMapper
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

class MetadataLottieGeneratorTask : DefaultTask() {
    @get:Input
    @Option(
        option = "lottieFile",
        description = "Relative path of the Lottie file"
    )
    lateinit var lottieFile: String

    @get:Input
    @Option(
        option = "themeFile",
        description = "Absolute path to theme map JSON file"
    )
    lateinit var themeFile: String

    private val themePathFactory: ThemePathFactory = DefaultThemePathFactory()

    @TaskAction
    fun generate() {
        val lottieFile = File(project.rootDir, lottieFile)
            .takeIf { it.exists() } ?: throw GradleException("Could not find Lottie file")
        val themeFile = File(themeFile)
            .takeIf { it.exists() } ?: throw GradleException("Could not find theme map file")
        val metadataFile = File(
            lottieFile.parent,
            "${lottieFile.nameWithoutExtension}_metadata.${lottieFile.extension}"
        )

        val lottieJson = lottieFile.readText()

        /**
         * {
         *   "strokeTokens": [
         *     {
         *       "name": "iconPrimary",
         *       "color": "0B1620"
         *     }
         *   ],
         *   "fillTokens": [
         *     {
         *       "name": "bgPrimary",
         *       "color": "FFFFFF"
         *     }
         *   ]
         * }
         */
        val themeJson = themeFile.readText()

        // TODO - create mapper with factory from json
        val themeMapper = object : ThemeMapper {
            override fun getThemeToken(property: String, colorValue: String): String? {
                return null
            }
        }

        // TODO - Parse theme mapper
        val themePaths: List<LottieThemePath> = themePathFactory.create(lottieJson, themeMapper)
        val metadata = LottieMetadata(themePaths = themePaths)

        // TODO - write text to file
        metadataFile.writeText("")
    }
}


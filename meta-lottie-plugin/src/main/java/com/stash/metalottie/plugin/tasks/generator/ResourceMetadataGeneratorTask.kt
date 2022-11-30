package com.stash.metalottie.plugin.tasks.generator

import com.stash.metalottie.plugin.tasks.generator.parser.MetadataParser
import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class ResourceMetadataGeneratorTask : DefaultTask() {
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

    @get:Input
    @Option(
        option = "pretty-print",
        description = "Make it pretty"
    )
    var prettyPrint: Boolean = false

    @TaskAction
    fun generate() {
        val lottieFile = File(project.rootDir, lottieFile)
            .takeIf { it.exists() } ?: throw GradleException("Could not find Lottie file")
        val themeFile = File(themeFile)
            .takeIf { it.exists() } ?: throw GradleException("Could not find theme map file")
        val json = jsonBuilder()
        val metadataFile = File(
            lottieFile.parent,
            "${lottieFile.nameWithoutExtension}_metadata.${lottieFile.extension}"
        )

        val metadata = MetadataParser.parse(lottieFile, themeFile)

        metadataFile.writeText(json.encodeToString(metadata))
    }

    fun jsonBuilder(): Json {
        return if (prettyPrint) {
            // pretty
            Json {
                prettyPrint = true
            }
        } else {
            // minify
            Json {
                prettyPrint = false
            }
        }
    }
}

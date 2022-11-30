package com.stash.metalottie.plugin.tasks.generator

import com.stash.metalottie.plugin.tasks.generator.parser.MetadataParser
import com.stash.metalottie.plugin.tasks.generator.writer.DynamicPropertiesWriter
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class ComposeDynamicPropertiesGeneratorTask : DefaultTask() {
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
        option = "sourceDir",
        description = "Absolute path to source directory when generated code will reside"
    )
    lateinit var srcDir: String

    @TaskAction
    fun generate() {
        val lottieFile = File(project.rootDir, lottieFile)
            .takeIf { it.exists() } ?: throw GradleException("Could not find Lottie file")
        val themeFile = File(themeFile)
            .takeIf { it.exists() } ?: throw GradleException("Could not find theme map file")
        val sourceDirectory = File(srcDir)
            .takeIf { it.exists() && it.isDirectory }
            ?: throw GradleException("Could not find directory $srcDir")

        val metadata = MetadataParser.parse(lottieFile, themeFile)
        DynamicPropertiesWriter().write(metadata, lottieFile.nameWithoutExtension, sourceDirectory)
    }
}

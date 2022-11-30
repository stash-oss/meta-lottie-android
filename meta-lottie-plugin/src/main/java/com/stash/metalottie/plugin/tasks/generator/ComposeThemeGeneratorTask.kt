package com.stash.metalottie.plugin.tasks.generator

import com.stash.metalottie.plugin.tasks.generator.parser.ThemeParser
import com.stash.metalottie.plugin.tasks.generator.writer.ComposeThemeWriter
import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class ComposeThemeGeneratorTask : DefaultTask() {
    @get:Input
    @Option(
        option = "themeFile",
        description = "Absolute path to theme map JSON file"
    )
    lateinit var themeFile: String

    @get:Input
    @Option(
        option = "themeName",
        description = "Name of theme provider"
    )
    var themeName: String = "MyThemeColorProvider"

    @get:Input
    @Option(
        option = "sourceDir",
        description = "Absolute path to source directory when generated code will reside"
    )
    lateinit var srcDir: String

    @TaskAction
    fun generate() {
        val themeFile = File(themeFile)
            .takeIf { it.exists() } ?: throw GradleException("Could not find theme map file")
        val sourceDirectory = File(srcDir)
            .takeIf { it.exists() && it.isDirectory }
            ?: throw GradleException("Could not find directory $srcDir")

        ComposeThemeWriter().write(ThemeParser.parse(themeFile), themeName, sourceDirectory)
    }
}

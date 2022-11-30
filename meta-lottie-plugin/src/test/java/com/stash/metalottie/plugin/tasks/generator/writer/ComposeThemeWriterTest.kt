package com.stash.metalottie.plugin.tasks.generator.writer

import com.stash.metalottie.plugin.tasks.generator.parser.ThemeParser
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ComposeThemeWriterTest {
    @Test
    fun `Validate compose theme generated code`() {
        val themeFile = File(
            this::class.java.classLoader!!
                .getResource("test_theme.json")!!
                .toURI()
        )
        val expected = String(
            Files.readAllBytes(
                Paths.get(
                    this::class.java.classLoader!!
                        .getResource("result_compose_theme_color_provider")!!
                        .toURI()
                )
            ), StandardCharsets.UTF_8
        )

        val actualBuilder = StringBuilder()

        ComposeThemeWriter().write(
            ThemeParser.parse(themeFile),
            "MyThemeColorProvider",
            "com.stash.metalottie.plugin",
            actualBuilder
        )

        assertEquals(expected, actualBuilder.toString())
    }
}
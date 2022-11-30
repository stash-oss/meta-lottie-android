package com.stash.metalottie.plugin.tasks.generator.writer

import com.stash.metalottie.plugin.tasks.generator.parser.MetadataParser
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class DynamicPropertiesWriterTest {
    @Test
    fun `Validate compose dynamic properties generated code`() {
        val lottieFile = File(
            this::class.java.classLoader!!
                .getResource("test_lottie.json")!!
                .toURI()
        )
        val themeFile = File(
            this::class.java.classLoader!!
                .getResource("test_theme.json")!!
                .toURI()
        )
        val expected = String(
            Files.readAllBytes(
                Paths.get(
                    this::class.java.classLoader!!
                        .getResource("result_compose_dynamic_properties")!!
                        .toURI()
                )
            ), StandardCharsets.UTF_8
        )

        val metadata = MetadataParser.parse(lottieFile, themeFile)
        val actualBuilder = StringBuilder()

        DynamicPropertiesWriter().write(
            metadata,
            "testLottie",
            "com.stash.metalottie.plugin",
            actualBuilder
        )

        Assertions.assertEquals(expected, actualBuilder.toString())
    }
}
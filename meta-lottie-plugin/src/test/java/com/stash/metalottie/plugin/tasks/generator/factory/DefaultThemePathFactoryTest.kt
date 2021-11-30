package com.stash.metalottie.plugin.tasks.generator.factory

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

internal class DefaultThemePathFactoryTest {

    @Test
    fun `test theme path construction`() {
        val lottieJson = String(Files.readAllBytes(Paths.get(this::class.java.classLoader!!.getResource("test_lottie.json")!!.toURI())), StandardCharsets.UTF_8)
        val themeMapJson = String(Files.readAllBytes(Paths.get(this::class.java.classLoader!!.getResource("test_theme.json")!!.toURI())), StandardCharsets.UTF_8)
        val themeMapper = DefaultThemeMapFactory(themeMapJson)
        val factory = DefaultThemePathFactory()

        val result = factory.create(lottieJson, themeMapper)

        assertEquals(7, result.size)
        assertEquals("STROKE_COLOR", result[0].property)
        assertEquals("background", result[0].themeKey)
        assertEquals("009CEB", result[0].color)
        assertEquals("Shape Layer 2", result[0].keyPath[0])
        assertEquals("Shape 1", result[0].keyPath[1])
        assertEquals("Stroke 1", result[0].keyPath[2])
        assertEquals("STROKE_COLOR", result[1].property)
        assertEquals("primary", result[1].themeKey)
        assertEquals("0B1620", result[1].color)
        assertEquals("circle", result[1].keyPath[0])
        assertEquals("Group 3", result[1].keyPath[1])
        assertEquals("Stroke 1", result[1].keyPath[2])
        assertEquals("FILL_COLOR", result[2].property)
        assertEquals("accent", result[2].themeKey)
        assertEquals("009CEB", result[2].color)
        assertEquals("stroke 2", result[2].keyPath[0])
        assertEquals("Ellipse 41", result[2].keyPath[1])
        assertEquals("Fill 1", result[2].keyPath[2])
        assertEquals("FILL_COLOR", result[3].property)
        assertEquals("primary", result[3].themeKey)
        assertEquals("000000", result[3].color)
        assertEquals("circSmall 8", result[3].keyPath[0])
        assertEquals("Ellipse 1", result[3].keyPath[1])
        assertEquals("Fill 1", result[3].keyPath[2])
        assertEquals("FILL_COLOR", result[4].property)
        assertEquals("primary", result[4].themeKey)
        assertEquals("000000", result[4].color)
        assertEquals("circSmall 7", result[4].keyPath[0])
        assertEquals("Ellipse 1", result[4].keyPath[1])
        assertEquals("Fill 1", result[4].keyPath[2])
        assertEquals("FILL_COLOR", result[5].property)
        assertEquals("primary", result[5].themeKey)
        assertEquals("000000", result[5].color)
        assertEquals("circSmall 6", result[5].keyPath[0])
        assertEquals("Ellipse 1", result[5].keyPath[1])
        assertEquals("Fill 1", result[5].keyPath[2])
        assertEquals("FILL_COLOR", result[6].property)
        assertEquals("primary", result[6].themeKey)
        assertEquals("000000", result[6].color)
        assertEquals("circSmall 5", result[6].keyPath[0])
        assertEquals("Ellipse 1", result[6].keyPath[1])
        assertEquals("Fill 1", result[6].keyPath[2])
    }
}
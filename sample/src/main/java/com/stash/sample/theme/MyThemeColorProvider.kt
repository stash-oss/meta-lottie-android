package com.stash.sample.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.stash.metalottie.compose.ColorType
import com.stash.metalottie.compose.ColorValueIdentifier
import com.stash.metalottie.compose.ThemeColorProvider

object MyThemeColorProvider : ThemeColorProvider {
    @Composable
    override fun getValue(identifier: ColorValueIdentifier): Int? {
        return when (identifier.type) {
            ColorType.STROKE -> getStrokeColorValue(key = identifier.key)
            ColorType.FILL -> getFillColorValue(key = identifier.key)
        }
    }

    @Composable
    private fun getStrokeColorValue(key: String): Int? {
        return when (key) {
            "primary" -> MaterialTheme.colors.primary.toArgb()
            "background" -> MaterialTheme.colors.primaryVariant.toArgb()
            "accent" -> MaterialTheme.colors.secondary.toArgb()
            else -> null
        }
    }

    @Composable
    private fun getFillColorValue(key: String): Int? {
        return when (key) {
            "primary" -> MaterialTheme.colors.primary.toArgb()
            "background" -> MaterialTheme.colors.primaryVariant.toArgb()
            "accent" -> MaterialTheme.colors.secondary.toArgb()
            else -> null
        }
    }
}

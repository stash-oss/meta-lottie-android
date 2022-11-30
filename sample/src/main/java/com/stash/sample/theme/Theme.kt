package com.stash.sample.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.stash.metalottie.compose.LocalMetaLottie
import com.stash.metalottie.compose.MetaLottie

private val DarkColorPalette = darkColors(
    primary = Color(0xFF5C6BC0),
    primaryVariant = Color(0xFF303F9F),
    secondary = Color(0xFF26C6DA),
    secondaryVariant = Color(0xFF0097A7)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFEC407A),
    primaryVariant = Color(0xFFC2185B),
    secondary = Color(0xFF26C6DA),
    secondaryVariant = Color(0xFF0097A7)
)

@Composable
fun MetaLottieTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalMetaLottie provides MetaLottie(MyThemeColorProvider)) {
        MaterialTheme(
            colors = colors,
            content = content
        )
    }
}
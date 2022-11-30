package com.stash.metalottie.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

data class MetaLottie(
    val themeColorProvider: ThemeColorProvider = object : ThemeColorProvider {
        @Composable
        override fun getValue(identifier: ColorValueIdentifier): Int? = null
    }
)

val LocalMetaLottie = compositionLocalOf { MetaLottie() }
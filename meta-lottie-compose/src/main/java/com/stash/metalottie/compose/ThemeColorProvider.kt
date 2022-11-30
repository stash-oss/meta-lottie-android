package com.stash.metalottie.compose

import androidx.compose.runtime.Composable

interface ThemeColorProvider {
    @Composable
    fun getValue(identifier: ColorValueIdentifier): Int?
}
package com.stash.metalottie.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LottieTheme(
    val keyPath: List<String>,
    val property: LottieThemeProperty,
    val themeKey: String,
    val color: String
)

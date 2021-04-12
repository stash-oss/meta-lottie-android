package com.stash.metalottie.plugin.tasks.generator.model

import kotlinx.serialization.Serializable

@Serializable
data class LottieThemePath(
    val keyPath: List<String>,
    val property: String,
    val themeKey: String,
    val color: String
)

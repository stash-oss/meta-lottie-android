package com.stash.metalottie.plugin.tasks.generator.model.theme

import kotlinx.serialization.Serializable

@Serializable
data class LottieInputThemeMapItem(
    val name: String,
    val color: String
)
package com.stash.metalottie.plugin.tasks.generator.model.theme

import kotlinx.serialization.Serializable

@Serializable
data class LottieInputThemeMap(
    val strokeColors: List<LottieInputThemeMapItem>,
    val fillColors: List<LottieInputThemeMapItem>
)

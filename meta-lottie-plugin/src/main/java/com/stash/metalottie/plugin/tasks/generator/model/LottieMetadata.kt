package com.stash.metalottie.plugin.tasks.generator.model

import kotlinx.serialization.Serializable

@Serializable
data class LottieMetadata(
    val themePaths: List<LottieThemePath>
)

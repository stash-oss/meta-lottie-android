package com.stash.metalottie.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LottieMetadata(
    val themePaths: List<LottieTheme>
)

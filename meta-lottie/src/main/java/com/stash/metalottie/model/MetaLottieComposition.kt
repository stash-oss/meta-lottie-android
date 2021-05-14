package com.stash.metalottie.model

import com.airbnb.lottie.LottieComposition

data class MetaLottieComposition(
    val composition: LottieComposition?,
    val themeValues: List<LottieThemeValue>
)

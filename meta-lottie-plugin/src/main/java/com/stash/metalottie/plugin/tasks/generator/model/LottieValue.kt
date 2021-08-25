package com.stash.metalottie.plugin.tasks.generator.model

sealed class LottieValue {
    data class Layer(val key: String, val preCompRefId: String? = null) : LottieValue()
    data class ColorValue(val property: String, val color: String) : LottieValue()
}

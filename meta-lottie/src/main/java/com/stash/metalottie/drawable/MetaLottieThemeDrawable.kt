package com.stash.metalottie.drawable

import com.airbnb.lottie.LottieDrawable
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.stash.metalottie.model.LottieThemeProperty.FILL_COLOR
import com.stash.metalottie.model.LottieThemeProperty.STROKE_COLOR
import com.stash.metalottie.model.LottieThemeValue
import com.stash.metalottie.model.MetaLottieComposition

/**
 * Lottie drawable that registers theme paths from Meta-Lottie metadata used to override lottie
 * colors with configured theme colors
 */
class MetaLottieThemeDrawable(metaLottieComposition: MetaLottieComposition) : LottieDrawable() {

    /**
     * Registers composition and color lookup callbacks
     */
    init {
        composition = metaLottieComposition.composition
        generateColorLookupCallbacks(metaLottieComposition.themeValues)
    }

    /**
     * Register color lookups for theme replacements
     *
     * @param themeValues theme color value replacements
     */
    private fun generateColorLookupCallbacks(themeValues: List<LottieThemeValue>) {
        themeValues.forEach { themeValue ->
            val keyPath = KeyPath(*themeValue.keyPath.toTypedArray())

            when (themeValue.property) {
                FILL_COLOR -> addValueCallback(
                    keyPath,
                    LottieProperty.COLOR
                ) { themeValue.value }
                STROKE_COLOR -> addValueCallback(
                    keyPath,
                    LottieProperty.STROKE_COLOR
                ) { themeValue.value }
            }
        }
    }
}

package com.stash.metalottie.model

import androidx.annotation.RawRes
import com.airbnb.lottie.FontAssetDelegate
import com.airbnb.lottie.LottieDrawable.RESTART
import com.airbnb.lottie.LottieDrawable.RepeatMode
import com.airbnb.lottie.TextDelegate
import com.stash.metalottie.model.MetaLottieConstants.NO_METADATA_RESOURCE

data class LottieResource(
    @RawRes val lottieRawRes: Int,
    @RawRes val lottieMetadataRawRes: Int = NO_METADATA_RESOURCE,
    @RepeatMode val repeatMode: Int = RESTART,
    val repeatCount: Int = 0,
    /** @see [FontAssetDelegate] */
    val fontAssetDelegate: FontAssetDelegate? = null,
    /** @see [TextDelegate] */
    val textDelegate: ((MetaLottieComposition) -> TextDelegate)? = null
)

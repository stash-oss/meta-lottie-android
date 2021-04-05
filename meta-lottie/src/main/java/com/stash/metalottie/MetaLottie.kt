package com.stash.metalottie

import android.widget.ImageView
import com.stash.metalottie.datastore.MetaLottieCache
import com.stash.metalottie.drawable.MetaLottieThemeDrawable
import com.stash.metalottie.factory.MetaLottieCompositionFactory
import com.stash.metalottie.model.LottieResource
import com.stash.metalottie.model.MetaLottieComposition
import com.stash.metalottie.theme.ThemeValueProvider
import java.lang.ref.WeakReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Meta-Lottie asset loader loader
 */
object MetaLottie {
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Main)

    /**
     * Loads lottie into an ImageView with support for Meta-Lottie metadata
     *
     * @param imageView Image view that will host the lottie
     * @param themeValueProvider Value provider for theme color replacements
     * @param lottieResource lottie resource definition
     * @param dispatcher coroutine dispatcher to load lottie resources (Defaults to IO dispatcher)
     */
    fun loadInto(
        imageView: ImageView,
        themeValueProvider: ThemeValueProvider,
        lottieResource: LottieResource,
        dispatcher: CoroutineDispatcher = IO
    ) {
        val weakImageView: WeakReference<ImageView> = WeakReference(imageView)

        scope.launch(dispatcher) {
            val intoImageView = weakImageView.get() ?: return@launch

            val result = MetaLottieCompositionFactory
                .fromRawRes(
                    intoImageView.context,
                    themeValueProvider,
                    lottieResource.lottieRawRes,
                    lottieResource.lottieMetadataRawRes
                )

            withContext(Main) {
                onImageResult(weakImageView.get(), result, lottieResource)
            }
        }
    }

    /**
     * Clear Meta-Lottie loading and cache
     */
    fun clear() {
        scope.coroutineContext.cancelChildren()
        MetaLottieCache.clear()
    }

    /**
     * Attaches Meta-Lottie drawable to an image view
     *
     * @param imageView Image view that will host the lottie
     * @param result MetaLottieComposition containing lottie and theme details
     * @param lottieResource lottie resource definition
     */
    private fun onImageResult(
        imageView: ImageView?,
        result: MetaLottieComposition,
        lottieResource: LottieResource
    ) {
        imageView?.let {
            val drawable = MetaLottieThemeDrawable(result).apply {
                repeatCount = lottieResource.repeatCount
                repeatMode = lottieResource.repeatMode
                playAnimation()
            }

            imageView.setImageDrawable(drawable)
        }
    }
}

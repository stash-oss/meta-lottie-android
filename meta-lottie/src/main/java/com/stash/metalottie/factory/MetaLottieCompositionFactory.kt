package com.stash.metalottie.factory

import android.content.Context
import androidx.annotation.RawRes
import com.airbnb.lottie.LottieCompositionFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.stash.metalottie.datastore.MetaLottieCache
import com.stash.metalottie.model.LottieMetadata
import com.stash.metalottie.model.LottieThemeValue
import com.stash.metalottie.model.MetaLottieComposition
import com.stash.metalottie.model.MetaLottieConstants.NO_METADATA_RESOURCE
import com.stash.metalottie.theme.ThemeValueProvider

/**
 * Meta-Lottie composition creation factory
 */
object MetaLottieCompositionFactory {

    /**
     * Meta-Lottie metadata serialization adapter
     */
    private val metadataAdapter: JsonAdapter<LottieMetadata> by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
            .adapter(LottieMetadata::class.java)
    }

    /**
     * Load Meta-Lottie Composition
     *
     * @param context Context for loading resources
     * @param themeValueProvider Value provider for theme color replacements
     * @param lottieRes lottie resource id
     * @param metadataRes Meta-Lottie metadata resource file
     * @return MetaLottieComposition
     */
    fun fromRawRes(
        context: Context,
        themeValueProvider: ThemeValueProvider,
        @RawRes lottieRes: Int,
        @RawRes metadataRes: Int = NO_METADATA_RESOURCE
    ): MetaLottieComposition {
        val metadata = loadThemeValues(context, metadataRes, themeValueProvider)
        val lottieResult = LottieCompositionFactory.fromRawResSync(context, lottieRes)
        return MetaLottieComposition(lottieResult.value, metadata)
    }

    /**
     * Load theme values associated with the metadata
     *
     * @param context Context for loading resources
     * @param metadataRes Meta-Lottie metadata resource file
     * @param themeValueProvider Value provider for theme color replacements
     */
    private fun loadThemeValues(
        context: Context,
        @RawRes metadataRes: Int,
        themeValueProvider: ThemeValueProvider
    ): List<LottieThemeValue> {
        if (metadataRes == NO_METADATA_RESOURCE) {
            return listOf()
        }

        // Attempt to load from cache otherwise attempt to parse
        val metadata: LottieMetadata? = if (MetaLottieCache.contains(metadataRes)) {
            MetaLottieCache[metadataRes]
        } else {
            val metadataJson = context.resources
                .openRawResource(metadataRes)
                .bufferedReader()
                .use {
                    it.readText()
                }

            try {
                metadataAdapter.fromJson(metadataJson)?.also {
                    MetaLottieCache.put(metadataRes, it)
                }
            } catch (exception: JsonDataException) {
                null
            }
        }

        return metadata?.let { lottieMetadata ->
            lottieMetadata.themePaths.mapNotNull {
                val themeColor = themeValueProvider.getValue(context, it.themeKey)
                themeColor?.let { tc ->
                    LottieThemeValue(
                        it.keyPath,
                        it.property,
                        tc
                    )
                }
            }
        } ?: listOf()
    }
}

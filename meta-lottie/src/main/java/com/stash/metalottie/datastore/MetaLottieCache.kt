package com.stash.metalottie.datastore

import androidx.annotation.RawRes
import androidx.collection.LruCache
import com.stash.metalottie.model.LottieMetadata

/**
 * Cache storing preloaded Meta-Lottie metadata files. Cache storage utilizes a LRU caching
 * mechanism.
 */
object MetaLottieCache {
    private const val MAX_CACHE_SIZE = 20

    private val cache = LruCache<Int, LottieMetadata>(MAX_CACHE_SIZE)

    /**
     * Retrieves Meta-Lottie metadata from the cache
     *
     * @param cacheKey Raw resource id of the Meta-Lottie file
     * @return Cached Meta-Lottie metadata or null if no cached metadata exists
     */
    operator fun get(@RawRes cacheKey: Int): LottieMetadata? {
        return cache[cacheKey]
    }

    /**
     * Add Meta-Lottie metadata to the cache
     *
     * @param cacheKey Raw resource id of the Meta-Lottie file
     * @param lottieMetadata Meta-Lottie metadata instance
     */
    fun put(@RawRes cacheKey: Int, lottieMetadata: LottieMetadata) {
        cache.put(cacheKey, lottieMetadata)
    }

    /**
     * Checks if Meta-Lottie metadata exists in the cache
     */
    fun contains(@RawRes cacheKey: Int): Boolean {
        return get(cacheKey) != null
    }

    /**
     * Clears the Meta-Lottie metadata cache
     */
    fun clear() {
        cache.evictAll()
    }
}

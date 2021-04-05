package com.stash.metalottie.theme

import android.content.Context

/**
 * Provides theme color value given given a theme key
 */
interface ThemeValueProvider {

    /**
     * Retrieves color value given a theme key
     *
     * @param context Context
     * @param key Theme key
     * @return color value or null if no value for theme key
     */
    fun getValue(context: Context, key: String): Int?
}

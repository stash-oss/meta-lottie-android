package com.stash.sample

import android.content.Context
import com.stash.metalottie.theme.ThemeValueProvider

class MyThemeValueProvider : ThemeValueProvider {
    companion object {
        val THEME_ATTR_MAP = mapOf(
            "primary" to R.color.color1,
            "accent" to R.color.color_accent_1,
            "background" to R.color.color2
        )
    }

    override fun getValue(context: Context, key: String): Int? {
        return THEME_ATTR_MAP[key]?.let { context.getColor(it) }
    }
}

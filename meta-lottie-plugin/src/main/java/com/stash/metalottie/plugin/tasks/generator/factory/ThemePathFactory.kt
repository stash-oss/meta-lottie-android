package com.stash.metalottie.plugin.tasks.generator.factory

import com.stash.metalottie.plugin.tasks.generator.model.LottieThemePath
import com.stash.metalottie.plugin.tasks.generator.model.theme.ThemeMapper

interface ThemePathFactory {
    fun create(lottieJson: String, themeMapper: ThemeMapper): List<LottieThemePath>
}

package com.stash.sample

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.stash.metalottie.MetaLottie
import com.stash.metalottie.model.LottieResource
import com.stash.metalottie.theme.ThemeValueProvider
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {
    private val themeValueProvider: ThemeValueProvider by lazy { MyThemeValueProvider() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        setUpLottie()

        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setUpLottie() {
        MetaLottie.loadInto(
            imageView = lottie1,
            themeValueProvider = themeValueProvider,
            lottieResource = LottieResource(
                lottieRawRes = R.raw.success,
                lottieMetadataRawRes = R.raw.success_metadata,
                repeatCount = ValueAnimator.INFINITE
            )
        )
    }
}

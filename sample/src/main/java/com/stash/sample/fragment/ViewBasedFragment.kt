package com.stash.sample.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.stash.metalottie.MetaLottie
import com.stash.metalottie.model.LottieResource
import com.stash.metalottie.theme.ThemeValueProvider
import com.stash.sample.MyThemeValueProvider
import com.stash.sample.R
import com.stash.sample.databinding.FragmentViewBasedBinding

class ViewBasedFragment : Fragment() {

    private val themeValueProvider: ThemeValueProvider by lazy { MyThemeValueProvider() }
    private var binding: FragmentViewBasedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewBasedBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding!!.toolbar.setTitle(R.string.view_fragment_title)
        binding!!.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        setUpLottie()

        binding!!.toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUpLottie() {
        MetaLottie.loadInto(
            imageView = binding!!.lottie1,
            themeValueProvider = themeValueProvider,
            lottieResource = LottieResource(
                lottieRawRes = R.raw.success,
                lottieMetadataRawRes = R.raw.success_metadata,
                repeatCount = ValueAnimator.INFINITE
            )
        )
    }
}
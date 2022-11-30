package com.stash.sample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.stash.sample.R
import com.stash.sample.databinding.FragmentSelectorBinding

class SelectorFragment : Fragment() {

    private var binding: FragmentSelectorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectorBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.viewButton.setOnClickListener {
            findNavController().navigate(R.id.viewBasedFragment)
        }

        binding!!.composeButton.setOnClickListener {
            findNavController().navigate(R.id.composeBasedFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
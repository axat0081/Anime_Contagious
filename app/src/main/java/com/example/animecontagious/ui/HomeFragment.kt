package com.example.animecontagious.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.animecontagious.R
import com.example.animecontagious.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        binding.apply {
            upcomingAnimeButton.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToMainFragment()
                findNavController().navigate(action)
            }
            airingAnimeButton.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToAiringFragment2()
                findNavController().navigate(action)
            }
        }
    }
}
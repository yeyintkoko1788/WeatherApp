package com.example.weatherapp.view.ui.sport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weatherapp.databinding.FragmentSportBinding
import com.example.weatherapp.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportFragment : BaseFragment<SportViewModel>() {
    override val viewModel: SportViewModel by viewModels()

    private lateinit var binding : FragmentSportBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSportBinding.inflate(inflater, container, false)
        return binding.root
    }
}
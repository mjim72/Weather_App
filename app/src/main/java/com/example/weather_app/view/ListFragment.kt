package com.example.weather_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_app.R
import com.example.weather_app.adapter.ClickListener
import com.example.weather_app.adapter.WeatherAdapter
import com.example.weather_app.databinding.FragmentListBinding
import com.example.weather_app.model.Result
import com.example.weather_app.util.Resource
import com.example.weather_app.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val weatherAdapter by lazy { WeatherAdapter(::itemClicked) }
    private lateinit var binding : FragmentListBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val args by navArgs<ListFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        setupBinding()
        loadWeather()
    }

    private fun setupBinding() = with(binding) {
        btnButton.setOnClickListener { findNavController().popBackStack() }
        rvWeather.apply { adapter = weatherAdapter }
    }

    private fun loadWeather() = with(viewModel) {
        fetchWeather(args.query)
        result.observe(viewLifecycleOwner, { result ->
            if (result is Resource.Success) weatherAdapter.setList(result.data)
            with(binding) {
                toolbarTitle.text = args.query
                progressBar.isVisible = result is Resource.Loading
                tvNotFound.isVisible = result is Resource.Error
                tvNotFound.text = if (result is Resource.Error) result.msg else ""
            }
        })
    }

    private fun itemClicked(result: Result) = with(ListFragmentDirections) {
        findNavController().navigate(actionListFragmentToDetailFragment(result, args.query))
    }
}
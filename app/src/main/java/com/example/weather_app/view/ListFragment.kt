package com.example.weather_app.view

import android.os.Bundle
import android.util.Log
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
class ListFragment : Fragment(), ClickListener {

    private lateinit var cityName : String
    private val weatherAdapter by lazy { WeatherAdapter() }
    private lateinit var binding : FragmentListBinding
    private val viewModel: WeatherViewModel by viewModels()
    private val args by navArgs<ListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        setupBinding()
        setupObserver()
        return binding.root
    }

    private fun setupBinding() {
        binding.apply {

            btnButton.setOnClickListener {
                findNavController().popBackStack()
            }

            rvWeather.apply {
                adapter = weatherAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupObserver() {
        viewModel.fetchWeather(args.query)
        viewModel.result.observe(viewLifecycleOwner, { result ->
            cityName = result.data?.city?.name.toString()
            binding.toolbarTitle.text = cityName
            result.data?.list?.let { it -> weatherAdapter.setAdapter(it, this) }

            binding.progressBar.isVisible = result is Resource.Loading && result.data == null
            binding.tvNotFound.isVisible = result is Resource.Error && result.data == null
            binding.tvNotFound.text = result.error
        })
    }

    override fun itemClicked(result: Result) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(result, cityName)
        findNavController().navigate(action)
    }
}
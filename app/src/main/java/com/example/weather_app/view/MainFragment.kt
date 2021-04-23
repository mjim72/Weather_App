package com.example.weather_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.weather_app.R
import com.example.weather_app.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setupBinding()

        return binding.root
    }

    private fun setupBinding() {
        binding.apply {
            btnSearch.setOnClickListener {
                if(etSearch.text.toString().isNotEmpty()) {
                    val action = MainFragmentDirections.actionMainFragmentToListFragment(etSearch.text.toString())
                    findNavController().navigate(action)
                    etSearchContainer.isErrorEnabled = false
                } else {
                    etSearchContainer.apply {
                        isErrorEnabled = true
                        error = "Enter city name"
                    }
                }
            }
        }
    }

}
package com.example.weather_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.model.Result
import com.example.weather_app.model.WeatherResponse
import com.example.weather_app.repository.WeatherRepository
import com.example.weather_app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ResponseCache
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _result = MutableLiveData<Resource<List<Result>>>()
    val result: LiveData<Resource<List<Result>>> get() = _result

    fun fetchWeather(query: String) {
        _result.postValue(Resource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            _result.postValue(weatherRepository.fetchWeather(query))
        }
    }
}
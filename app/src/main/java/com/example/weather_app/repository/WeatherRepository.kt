package com.example.weather_app.repository

import com.example.weather_app.model.Result
import com.example.weather_app.model.WeatherResponse
import com.example.weather_app.remote.WeatherApi
import com.example.weather_app.util.Resource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {

    suspend fun fetchWeather(query: String) = try {
        val body = weatherApi.fetchWeather(query).body()
        if (body?.cod == "200" && body.list.isNullOrEmpty().not()) Resource.Success(body.list)
        else Resource.Error("City not found")
    } catch (e: Exception) {
        Resource.Error("Couldn't reach the server")
    }
}
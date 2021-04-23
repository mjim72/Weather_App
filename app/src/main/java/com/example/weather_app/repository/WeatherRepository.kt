package com.example.weather_app.repository

import com.example.weather_app.model.Result
import com.example.weather_app.model.WeatherResponse
import com.example.weather_app.remote.WeatherApi
import com.example.weather_app.util.Resource
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {

    suspend fun fetchWeather(query: String) : Resource<WeatherResponse>? {
        return try {
            val body = weatherApi.fetchWeather(query).body()
            when(body?.cod) {
                "200" -> Resource.Success(body)
                else -> Resource.Error("City not found", null)
            }
        } catch (e: Exception) {
            Resource.Error("Couldn't reach the server")
        }

    }
}
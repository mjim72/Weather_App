package com.example.weather_app.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Result>,
    val message: Int
)
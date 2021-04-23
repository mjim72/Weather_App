package com.example.weather_app.adapter

import com.example.weather_app.model.Result

interface ClickListener {
    fun itemClicked(result: Result)
}
package com.example.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetails(
    val id: Int,
    val main: Main,
    val name: String,
    val weather: List<WeatherX>
)

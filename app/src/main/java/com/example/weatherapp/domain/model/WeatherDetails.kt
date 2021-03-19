package com.example.weatherapp.domain.model

import kotlinx.serialization.Serializable

// TODO: critical: Separate network models from presentation models.
@Serializable
data class WeatherDetails(
    val id: Int,
    val main: Main,
    val name: String,
    val weather: List<WeatherX>
)

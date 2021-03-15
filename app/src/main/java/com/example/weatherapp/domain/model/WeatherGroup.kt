package com.example.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherGroup(
    val count: Int,
    val list: List<WeatherDetails>
)

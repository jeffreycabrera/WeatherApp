package com.example.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Main(
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

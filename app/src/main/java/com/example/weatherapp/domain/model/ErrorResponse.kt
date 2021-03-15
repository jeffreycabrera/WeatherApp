package com.example.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val cod: String? = null,
    val message: String? = null
)

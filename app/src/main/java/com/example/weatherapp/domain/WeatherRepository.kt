package com.example.weatherapp.domain

import com.example.weatherapp.domain.model.WeatherDetails

interface WeatherRepository {
    suspend fun getWeatherList(group: String): List<WeatherDetails>
    suspend fun getWeatherDetails(id: String): WeatherDetails
}

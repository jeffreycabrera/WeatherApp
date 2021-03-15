package com.example.weatherapp.domain

import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.domain.network.WeatherService

private const val APP_ID = "4131c9fdc785b017a6df359cca9103e0"
private const val UNITS = "metric"
class WeatherRepositoryImpl(
    private val apiService: WeatherService
) : WeatherRepository {
    override suspend fun getWeatherList(group: String): List<WeatherDetails> {
        return apiService.getWeatherGroup(group, APP_ID, UNITS).list
    }

    override suspend fun getWeatherDetails(id: String): WeatherDetails {
        return apiService.getDetails(id, APP_ID, UNITS)
    }
}

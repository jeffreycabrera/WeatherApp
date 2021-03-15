package com.example.weatherapp.domain.network

import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.domain.model.WeatherGroup
import retrofit2.http.*

interface WeatherService {
    @GET("group")
    suspend fun getWeatherGroup(
        @Query("id") group: String,
        @Query("appid") appId: String,
        @Query(value = "units") units: String
    ): WeatherGroup

    @GET("weather")
    suspend fun getDetails(
        @Query("id") cityId: String,
        @Query("appid") appId: String,
        @Query(value = "units") units: String
    ): WeatherDetails
}

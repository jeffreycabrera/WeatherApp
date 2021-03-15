package com.example.weatherapp

import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.WeatherRepositoryImpl
import com.example.weatherapp.domain.network.WeatherService
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {

    private lateinit var apiService: WeatherService

    private lateinit var underTest: WeatherRepository

    @Before
    fun setup() {
        apiService = apiServiceMock()
        underTest = WeatherRepositoryImpl(apiService)
    }

    @Test
    fun `test getWeatherList()`() = runBlocking {
        val apiServiceResult = apiService.getWeatherGroup("", "", "").list
        val valueToReturn = underTest.getWeatherList("test")
        Assert.assertEquals(apiServiceResult, valueToReturn)
    }

    @Test
    fun `test getDetails()`() = runBlocking {
        val apiServiceResult = apiService.getDetails("", "", "")
        val valueToReturn = underTest.getWeatherDetails("test")
        Assert.assertEquals(apiServiceResult, valueToReturn)
    }
}

package com.example.weatherapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.ui.BaseViewModel
import com.example.weatherapp.ui.City

class WeatherListViewModel(
    private val repository: WeatherRepository
) : BaseViewModel() {

    private var _weatherListLiveData = MutableLiveData<List<WeatherDetails>>()
    val weatherListLiveData: LiveData<List<WeatherDetails>> = _weatherListLiveData

    init {
        getWeatherList()
    }

    fun getWeatherList() {
        val listOfCityIds = listOf(
            City.MANILA.id,
            City.PRAGUE.id,
            City.SEOUL.id
        // Nice `joinToString`, `{ it }` could be omitted though
        ).joinToString(separator = ",") { it }
        launch {
            _weatherListLiveData.value = repository.getWeatherList(listOfCityIds)
        }
    }
}

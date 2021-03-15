package com.example.weatherapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.ui.BaseViewModel

class WeatherDetailsViewModel(
    private val cityId: String?,
    private val repository: WeatherRepository
) : BaseViewModel() {

    private var _weatherDetailsLiveData = MutableLiveData<WeatherDetails>()
    val weatherDetailsLiveData: LiveData<WeatherDetails> = _weatherDetailsLiveData

    init {
        getDetails()
    }

    fun getDetails() {
        launch {
            cityId?.let {
                _weatherDetailsLiveData.value = repository.getWeatherDetails(it)
            }
        }
    }
}

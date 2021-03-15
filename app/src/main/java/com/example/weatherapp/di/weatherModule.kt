package com.example.weatherapp.di

import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.WeatherRepositoryImpl
import com.example.weatherapp.domain.network.WeatherService
import com.example.weatherapp.ui.PreferenceConfiguration
import com.example.weatherapp.ui.PreferenceConfigurationImpl
import com.example.weatherapp.ui.details.WeatherDetailsViewModel
import com.example.weatherapp.ui.list.WeatherListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val weatherModule = module {

    viewModel {
        WeatherListViewModel(
            repository = get()
        )
    }

    viewModel { (cityId: String?) ->
        WeatherDetailsViewModel(
            cityId = cityId,
            repository = get()
        )
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            apiService = get()
        )
    }

    single { get<Retrofit>().create(WeatherService::class.java) }
}

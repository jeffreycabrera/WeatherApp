package com.example.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.model.Main
import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.domain.model.WeatherGroup
import com.example.weatherapp.domain.model.WeatherX
import com.example.weatherapp.ui.City
import com.example.weatherapp.ui.list.WeatherListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var repository: WeatherRepository

    private lateinit var viewModel: WeatherListViewModel

    private val details = WeatherDetails(
        id = 12345,
        main = Main(123.1, 123.1, 123.1),
        name = "test3",
        weather = listOf(
            WeatherX(
                description = "description",
                icon = "",
                id = 12345,
                main = "main"
            )
        )
    )

    private val listOfCityIds = listOf(
        City.MANILA.id,
        City.PRAGUE.id,
        City.SEOUL.id
    ).joinToString(separator = ",") { it }

    private val group = WeatherGroup(
        count = 3,
        list = listOf(details)
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = WeatherListViewModel(repository)
    }

    @Test
    fun `test getWeatherList()`() {

        coEvery { repository.getWeatherList(listOfCityIds) }.coAnswers {
            group.list
        }
        runBlocking {
            viewModel.getWeatherList()
        }
        coVerify { repository.getWeatherList(listOfCityIds) }
    }
}

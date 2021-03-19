package com.example.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.domain.model.Main
import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.domain.model.WeatherX
import com.example.weatherapp.ui.details.WeatherDetailsViewModel
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
class WeatherDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var repository: WeatherRepository

    private lateinit var viewModel: WeatherDetailsViewModel

    // TODO: low: Property 'cityId' could be private
    val cityId = "12345"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
        viewModel = WeatherDetailsViewModel(cityId, repository)
    }

    @Test
    fun `test getDetails()`() {
        coEvery { repository.getWeatherDetails(cityId) }.coAnswers {
            WeatherDetails(
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
        }
        runBlocking {
            viewModel.getDetails()
        }
        coVerify { repository.getWeatherDetails(cityId) }
    }
}

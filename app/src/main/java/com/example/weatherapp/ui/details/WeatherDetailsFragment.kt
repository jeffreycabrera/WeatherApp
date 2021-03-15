package com.example.weatherapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.ui.BaseFragment
import com.example.weatherapp.ui.Constants.ONE_DECIMAL_PLACE
import com.example.weatherapp.ui.PreferenceConfiguration
import com.example.weatherapp.ui.PreferenceConfigurationImpl
import com.example.weatherapp.util.PrefConfig
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.roundToInt

class WeatherDetailsFragment : BaseFragment() {

    private val args by navArgs<WeatherDetailsFragmentArgs>()

    private lateinit var binding: FragmentWeatherDetailsBinding

    private val prefConfig: PreferenceConfiguration by lazy {
        PreferenceConfigurationImpl(requireContext())
    }

    override val viewModel by viewModel<WeatherDetailsViewModel> {
        parametersOf(args.cityId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val favoriteCities = PrefConfig.getFavorites(requireContext())

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getDetails()
        }

        viewModel.apply {
            isLoadingLiveData.observe(viewLifecycleOwner, { isLoading ->
                binding.swipeRefreshLayout.isRefreshing = isLoading
            })

            weatherDetailsLiveData.observe(viewLifecycleOwner, { weatherDetails ->
                val cityId = weatherDetails.id.toString()
                val cityName = weatherDetails.name
                val temperature = weatherDetails.main.temp
                val maxTemperature = weatherDetails.main.temp_max.roundToInt()
                val minTemperature = weatherDetails.main.temp_min.roundToInt()
                val weatherCondition = weatherDetails.weather.first().main

                binding.apply {
                    cityNameTextView.text = cityName
                    weatherConditionTextView.text = weatherCondition
                    temperatureTextView.text = getString(
                        R.string.temperature,
                        String.format(ONE_DECIMAL_PLACE, temperature)
                    )
                    weatherPeak.text =
                        getString(
                            R.string.temperature_over,
                            maxTemperature.toString(),
                            minTemperature.toString()
                        )

                    favoriteToggleButton.isChecked = favoriteCities.contains(cityId)

                    favoriteToggleButton.setOnClickListener {
                        if (favoriteToggleButton.isChecked) {
                            if (!favoriteCities.contains(cityId)) {
                                prefConfig.addFavorite(
                                    favoriteCities,
                                    cityId
                                )
                            }
                        } else {
                            prefConfig.removeFavorite(
                                favoriteCities,
                                cityId
                            )
                        }
                    }
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}

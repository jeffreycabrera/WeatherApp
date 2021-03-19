package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ItemWeatherBinding
import com.example.weatherapp.domain.model.WeatherDetails
import com.example.weatherapp.ui.Constants.ONE_DECIMAL_PLACE
import com.example.weatherapp.ui.PreferenceConfiguration
import com.example.weatherapp.ui.PreferenceConfigurationImpl
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlin.math.roundToInt

// TODO: low: Mixing Network adapters and RecyclerView adapters in a single package...
class WeatherListAdapter : RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    private lateinit var binding: ItemWeatherBinding

    // TODO: high: This would have been much better if it was handled already in the ViewModel.
    private val prefConfig: PreferenceConfiguration by lazy {
        PreferenceConfigurationImpl(binding.root.context)
    }

    // TODO: low: Probably overkill to set up a Subject. Could be just a callback passed via constructor.
    //  This is unlike in ViewModels that are observed from Fragments. ViewModels and Fragments have
    //  different lifespans and it is important to be able to detach Fragment and reattach the new
    //  Fragment to the same data source as before. But we don't have such a limitation here.
    //  Adapter is destroyed together with Fragment and recreated together with the new Fragment as
    //  well.
    private val onClickSubject: PublishSubject<String> = PublishSubject.create()

    // TODO: low: Use Kotlin `List` and `MutableList` instead. 2x
    private var weatherList: ArrayList<WeatherDetails> = arrayListOf()

    // TODO: low: Unused.
    private var favoriteCities: ArrayList<String> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherViewHolder {
        binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherDetails = weatherList[position]
        val temperature = weatherDetails.main.temp
        binding.apply {

            cityNameTextView.text = weatherDetails.name
            temperatureTextView.text =
                root.context.getString(
                    R.string.temperature,
                    // TODO: low: "%s°C" could have been directly defined as "%.1f°C" and omit `String.format`. 2x
                    String.format(ONE_DECIMAL_PLACE, temperature)
                )
            weatherConditionTextView.text = weatherDetails.weather[0].main

            val favoriteCites = prefConfig.getFavorites()

            root.apply {
                // TODO: low: Just call `setBackgroundResource` and skip `ContextCompat.getColor`.
                setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        getColorByTemperature(temperature.roundToInt())
                    )
                )
                setOnClickListener {
                    onClickSubject.onNext(weatherDetails.id.toString())
                }
                favoriteImageView.isVisible = favoriteCites.contains(weatherDetails.id.toString())
            }
        }
    }

    override fun getItemCount() = weatherList.size

    class WeatherViewHolder(binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun getPositionClickObservable(): Observable<String?> {
        return onClickSubject.hide()
    }

    fun add(list: List<WeatherDetails>) {
        weatherList.addAll(list)
        notifyDataSetChanged()
    }

    // TODO: low: Function "addFavoriteCities" is never used
    fun addFavoriteCities(list: List<String>) {
        favoriteCities.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        weatherList.clear()
        notifyDataSetChanged()
    }

    // TODO: medium: Temperature is decimal number, why truncate to int?
    private fun getColorByTemperature(temperature: Int): Int {
        return when {
            temperature <= 0 -> R.color.freezing
            temperature in 0..15 -> R.color.cold
            temperature in 15..30 -> R.color.warm
            else -> R.color.hot
        }
    }
}

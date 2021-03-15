package com.example.weatherapp.ui

import android.content.Context
import com.example.weatherapp.util.PrefConfig

class PreferenceConfigurationImpl(private val context: Context) : PreferenceConfiguration {
    override fun addFavorite(
        oldValueList: ArrayList<String>,
        newValueString: String
    ) {
        PrefConfig.addFavorite(context, oldValueList, newValueString)
    }

    override fun removeFavorite(
        oldValueList: ArrayList<String>,
        newValueString: String
    ) {
        PrefConfig.removeFavorite(context, oldValueList, newValueString)
    }

    override fun getFavorites(): ArrayList<String> {
        return PrefConfig.getFavorites(context)
    }
}

package com.example.weatherapp.ui

interface PreferenceConfiguration {
    fun addFavorite(oldValueList: ArrayList<String>, newValueString: String)
    fun removeFavorite(oldValueList: ArrayList<String>, newValueString: String)
    fun getFavorites(): ArrayList<String>
}

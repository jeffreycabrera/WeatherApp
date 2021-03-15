package com.example.weatherapp.util

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PrefConfig {
    private const val LIST_KEY = "LIST_KEY"

    fun addFavorite(context: Context, oldValueList: ArrayList<String>, newValueString: String) {
        val list = arrayListOf<String>()
        list.addAll(oldValueList)
        list.add(newValueString)
        setPref(context, list)
    }

    fun getFavorites(context: Context): ArrayList<String> {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val jsonString = preferences.getString(LIST_KEY, "")
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson<ArrayList<String>>(jsonString, type) ?: arrayListOf()
    }

    fun removeFavorite(context: Context, oldValueList: ArrayList<String>, newValueString: String) {
        val list = arrayListOf<String>()
        list.addAll(oldValueList)
        list.remove(newValueString)
        setPref(context, list)
    }

    private fun setPref(context: Context, list: ArrayList<String>) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().apply {
            putString(LIST_KEY, Gson().toJson(list))
            apply()
        }
    }
}

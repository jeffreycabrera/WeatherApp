package com.example.weatherapp.util

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: medium: Convert from Singleton to a class that accepts `Context` in a constructor.
//  Provide the instance via DI.
object PrefConfig {
    private const val LIST_KEY = "LIST_KEY"

    // TODO: medium: Why do you depend on the caller to provide you the previous data? You should
    //  be able to get that data yourself. Encapsulate. Don't make the API complicated like this. 2x
    fun addFavorite(context: Context, oldValueList: ArrayList<String>, newValueString: String) {
        val list = arrayListOf<String>()
        list.addAll(oldValueList)
        list.add(newValueString)
        setPref(context, list)
    }

    fun getFavorites(context: Context): ArrayList<String> {
        // TODO: medium: 'PreferenceManager' is deprecated. Deprecated in Java. 2x
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        // TODO: medium: Always be cautious when you store JSON for persistence reasons.
        //  There are no safeguards when the structure of the data changes. SQL databases
        //  support migration.
        //  In this case, you don't even need JSON. You could just use `preferences.getStringSet`
        //  and `preferences.edit().putStringSet()`.
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

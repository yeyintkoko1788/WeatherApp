package com.example.weatherapp.network.component

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class MyPreference @Inject constructor(private val context : Context) {
    private val PREFERENCES_NAME = "weather_preferences"

    private val sharedPreferences : SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFERENCES_NAME , Context.MODE_PRIVATE)

    fun saveLatLonData(lat : Double , lon : Double) {
        val editor = sharedPreferences.edit()
        editor.putFloat("LAT", lat.toFloat())
        editor.putFloat("LON", lon.toFloat())
        editor.apply()  // or editor.commit()
    }

    fun getLat() : Float {
        return sharedPreferences.getFloat("LAT", 0.0f)
    }

    fun getLon() : Float {
        return sharedPreferences.getFloat("LON", 0.0f)
    }
}
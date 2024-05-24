package com.example.weatherapp.view

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {
    companion object {
        val TAG = WeatherApplication::class.java
        lateinit var instance : WeatherApplication

        fun getContext(): Context {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AndroidThreeTen.init(this)
    }
}
package com.example.weatherapp.data.datasource.network

import com.example.weatherapp.domain.model.weather.WeatherVO

interface WeatherDataSource {

    suspend fun getTestWeather() : Result<WeatherVO>
}
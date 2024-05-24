package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.domain.model.weather.WeatherVO

interface WeatherRepository {
    suspend fun getWeather() : Result<WeatherVO>

    suspend fun getSearchResult(query: String) : Result<List<CityVO>>
}
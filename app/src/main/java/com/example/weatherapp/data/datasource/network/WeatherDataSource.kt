package com.example.weatherapp.data.datasource.network

import com.example.weatherapp.domain.model.astronomy.AstronomyVO
import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.domain.model.weather.WeatherVO
import retrofit2.http.Query
import javax.inject.Named

interface WeatherDataSource {

    suspend fun getTestWeather() : Result<WeatherVO>

    suspend fun getSearchWeather(query: String) : Result<List<CityVO>>

    suspend fun getAstronomy(query : String, dt : String) : Result<AstronomyVO>
}
package com.example.weatherapp.network.service

import com.example.weatherapp.network.response.WeatherResponse
import retrofit2.http.GET

interface TestService {
    @GET("current.json")
    suspend fun getWeather() : WeatherResponse

}
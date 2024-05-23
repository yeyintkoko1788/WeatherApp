package com.example.weatherapp.network.service

import com.example.weatherapp.network.response.WeatherResponse
import retrofit2.http.GET

interface TestService {
    @GET("current.json?key=b0b3722fe71245d2b2e53841242305&q=London&aqi=no")
    suspend fun getWeather() : WeatherResponse

}
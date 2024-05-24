package com.example.weatherapp.network.service

import com.example.weatherapp.network.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Named

interface WeatherService {
    @GET("search.json")
    suspend fun searchCities(@Query("key") token : String?,@Query("q") query: String): List<SearchResponse>
}
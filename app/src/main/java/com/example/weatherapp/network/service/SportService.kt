package com.example.weatherapp.network.service

import com.example.weatherapp.network.response.SportResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SportService {
    @GET("sports.json")
    suspend fun getSports(@Query("key") token : String?, @Query("q") type : String?): SportResponse
}
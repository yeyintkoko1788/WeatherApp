package com.example.weatherapp.network.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("location")
    val location : Location
)

data class Location(
    @SerializedName("name")
    val name : String
)
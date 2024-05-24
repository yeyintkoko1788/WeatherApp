package com.example.weatherapp.domain.model.city

data class CityVO(
    val id : Long,
    val name : String,
    val region : String,
    val country : String,
    val lat : Double,
    val lon : Double,
    val url : String
) {
}
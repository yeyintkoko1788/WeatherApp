package com.example.weatherapp.domain.model.astronomy

data class AstronomyVO(
    val region : String,
    val country : String,
    val distance : String,
    val localTime : String,
    val sunrise : String,
    val sunset : String,
    val moonrise : String,
    val moonset : String,
    val timeDffSunriseAndMoonrise : String,
    val timeDffSunsetAndMoonset : String
)
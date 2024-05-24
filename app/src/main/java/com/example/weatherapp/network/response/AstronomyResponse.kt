package com.example.weatherapp.network.response

import com.google.gson.annotations.SerializedName

data class AstronomyResponse(
    @SerializedName("location")
    val location : LocationItem,
    @SerializedName("astronomy")
    val astronomy : AstronomyItem
)

data class LocationItem(
    @SerializedName("name")
    val name : String,
    @SerializedName("region")
    val region : String,
    @SerializedName("country")
    val country : String,
    @SerializedName("lat")
    val lat : Double,
    @SerializedName("lon")
    val lon : Double,
    @SerializedName("tz_id")
    val tz_id : String,
    @SerializedName("localtime_epoch")
    val localtime_epoch : Int,
    @SerializedName("localtime")
    val localtime : String
)

data class AstronomyItem(
    @SerializedName("astro")
    val astro : Astro
)

data class Astro(
    @SerializedName("sunrise")
    val sunrise : String,
    @SerializedName("sunset")
    val sunset : String,
    @SerializedName("moonrise")
    val moonrise : String,
    @SerializedName("moonset")
    val moonset : String,
    @SerializedName("moon_phase")
    val moon_phase : String,
    @SerializedName("moon_illumination")
    val moon_illumination : String
)

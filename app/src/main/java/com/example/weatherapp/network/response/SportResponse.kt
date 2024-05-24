package com.example.weatherapp.network.response

import com.google.gson.annotations.SerializedName

data class SportResponse(
    @SerializedName("football")
    val football : List<Sport>,
    @SerializedName("cricket")
    val cricket : List<Sport>,
    @SerializedName("golf")
    val golf : List<Sport>
)

data class Sport(
    @SerializedName("stadium")
    val stadium : String,
    @SerializedName("country")
    val country : String,
    @SerializedName("region")
    val region : String,
    @SerializedName("tournament")
    val tournament : String,
    @SerializedName("start")
    val start : String,
    @SerializedName("match")
    val match : String
)
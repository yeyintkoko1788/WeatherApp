package com.example.weatherapp.network.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("id")
    val id : Long,

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

    @SerializedName("url")
    val url : String
)

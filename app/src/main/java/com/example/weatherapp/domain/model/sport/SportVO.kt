package com.example.weatherapp.domain.model.sport

data class SportVO(
    val footBall : List<SportItemVO>,
    val cricket : List<SportItemVO>,
    val golf : List<SportItemVO>
)

data class SportItemVO(
    val stadium : String,
    val country : String,
    val tourName : String,
    val date : String,
    val match : String
)
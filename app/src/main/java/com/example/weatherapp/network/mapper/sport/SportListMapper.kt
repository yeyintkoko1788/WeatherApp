package com.example.weatherapp.network.mapper.sport

import com.example.weatherapp.domain.model.sport.SportItemVO
import com.example.weatherapp.domain.model.sport.SportVO
import com.example.weatherapp.network.mapper.UnidirectionalMap
import com.example.weatherapp.network.response.SportResponse
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class SportListMapper @Inject constructor() : UnidirectionalMap<SportResponse, SportVO> {
    override fun map(data: SportResponse): SportVO {
        return SportVO(
            footBall = data.football.map {
                SportItemVO(
                    stadium = it.stadium,
                    country = it.country,
                    tourName = it.tournament,
                    date = getDate(it.start),
                    match = it.match
                )
            },
            cricket = data.cricket.map {
                SportItemVO(
                    stadium = it.stadium,
                    country = it.country,
                    tourName = it.tournament,
                    date = getDate(it.start),
                    match = it.match
                )
            },
            golf = data.golf.map {
                SportItemVO(
                    stadium = it.stadium,
                    country = it.country,
                    tourName = it.tournament,
                    date = getDate(it.start),
                    match = it.match
                )
            }
        )
    }
}

fun getDate(date : String) : String {
    val convertFormat = SimpleDateFormat("yyyy/MM/dd" , Locale.ENGLISH)
    val sourceFormat = SimpleDateFormat("yyyy-MM-dd H:mm" , Locale.ENGLISH)

    return try {
        convertFormat.format(sourceFormat.parse(date)!!)
    } catch (e : Exception) {
        ""
    }
}
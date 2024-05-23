package com.example.weatherapp.network.mapper.weather

import com.example.weatherapp.domain.model.weather.WeatherVO
import com.example.weatherapp.network.mapper.UnidirectionalMap
import com.example.weatherapp.network.response.WeatherResponse
import javax.inject.Inject

class WeatherMapper @Inject constructor() :
    UnidirectionalMap<WeatherResponse, WeatherVO> {
    override fun map(data: WeatherResponse): WeatherVO {
        return WeatherVO(
            name = data.location.name,
        )
    }
}
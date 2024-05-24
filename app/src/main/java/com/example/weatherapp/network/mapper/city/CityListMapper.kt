package com.example.weatherapp.network.mapper.city

import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.network.mapper.UnidirectionalMap
import com.example.weatherapp.network.response.SearchResponse
import javax.inject.Inject

class CityListMapper @Inject constructor() : UnidirectionalMap<List<SearchResponse>,List<CityVO>> {
    override fun map(data: List<SearchResponse>): List<CityVO> {
        return data.map {
            CityVO(it.id,it.name,it.region,it.country,it.lat,it.lon,it.url)
        }
    }
}
package com.example.weatherapp.data.repository

import com.example.weatherapp.data.datasource.network.WeatherDataSource
import com.example.weatherapp.domain.model.astronomy.AstronomyVO
import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.domain.model.weather.WeatherVO
import com.example.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository{
    override suspend fun getWeather(): Result<WeatherVO> {
        return Result.runCatching {
            weatherDataSource.getTestWeather().getOrThrow()
        }
    }

    override suspend fun getSearchResult(query: String): Result<List<CityVO>> {
        return Result.runCatching {
            weatherDataSource.getSearchWeather(query).getOrThrow()
        }
    }

    override suspend fun getAstronomy(query: String, dt: String): Result<AstronomyVO> {
        return Result.runCatching {
            weatherDataSource.getAstronomy(query,dt).getOrThrow()
        }
    }
}
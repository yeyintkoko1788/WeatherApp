package com.example.weatherapp.network.datasource

import com.example.weatherapp.data.datasource.network.WeatherDataSource
import com.example.weatherapp.domain.model.weather.WeatherVO
import com.example.weatherapp.network.mapper.weather.WeatherMapper
import com.example.weatherapp.network.service.TestService
import com.github.davidepanidev.kotlinextensions.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(
    private val weatherService: TestService,
    private val weatherMapper: WeatherMapper,
    private val dispatchers : DispatcherProvider
) : WeatherDataSource {
    override suspend fun getTestWeather(): Result<WeatherVO> {
        return Result.runCatching {
            val liveData = withContext(dispatchers.io){
                weatherService.getWeather()
            }
            weatherMapper.map(liveData)
        }
    }
}
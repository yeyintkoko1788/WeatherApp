package com.example.weatherapp.network.datasource

import com.example.weatherapp.data.datasource.network.WeatherDataSource
import com.example.weatherapp.domain.exception.FlowGenericErrorMessageFactory
import com.example.weatherapp.domain.model.astronomy.AstronomyVO
import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.domain.model.weather.WeatherVO
import com.example.weatherapp.network.mapper.astronomy.AstronomyMapper
import com.example.weatherapp.network.mapper.city.CityListMapper
import com.example.weatherapp.network.mapper.weather.WeatherMapper
import com.example.weatherapp.network.service.TestService
import com.example.weatherapp.network.service.WeatherService
import com.github.davidepanidev.kotlinextensions.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class WeatherDataSourceImpl @Inject constructor(
    private val testService: TestService,
    private val weatherService: WeatherService,
    private val cityListMapper: CityListMapper,
    private val weatherMapper: WeatherMapper,
    private val dispatchers : DispatcherProvider,
    private val astronomyMapper: AstronomyMapper,
    @Named("weather_key") private val weatherKey : String
) : WeatherDataSource {

    override suspend fun getTestWeather(): Result<WeatherVO> {
        return Result.runCatching {
            val liveData = withContext(dispatchers.io){
                testService.getWeather()
            }
            weatherMapper.map(liveData)
        }
    }

    override suspend fun getSearchWeather(query: String): Result<List<CityVO>> {
        return Result.runCatching {
            val liveData = withContext(dispatchers.io){
                weatherService.searchCities(weatherKey, query)
            }
            cityListMapper.map(liveData)
        }
    }

    override suspend fun getAstronomy(query: String, dt: String): Result<AstronomyVO> {
        return Result.runCatching {
            val liveData = withContext(dispatchers.io){
                weatherService.getAstronomy(weatherKey, query, dt)
            }
            astronomyMapper.map(liveData)
        }
    }
}
package com.example.weatherapp.network.datasource

import com.example.weatherapp.data.datasource.network.SportDataSource
import com.example.weatherapp.domain.model.sport.SportVO
import com.example.weatherapp.network.mapper.sport.SportListMapper
import com.example.weatherapp.network.service.SportService
import com.github.davidepanidev.kotlinextensions.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class SportDataSourceImpl @Inject constructor(
    private val sportService: SportService,
    private val sportListMapper: SportListMapper,
    private val dispatchers : DispatcherProvider,
    @Named("weather_key") private val weatherKey : String
) : SportDataSource {
    override suspend fun getSport(query : String): Result<SportVO> {
        return Result.runCatching {
            val response = withContext(dispatchers.io){
                sportService.getSports(weatherKey, query)
            }
            sportListMapper.map(response)
        }
    }
}
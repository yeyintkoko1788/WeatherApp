package com.example.weatherapp.data.repository

import com.example.weatherapp.data.datasource.network.SportDataSource
import com.example.weatherapp.domain.model.sport.SportVO
import com.example.weatherapp.domain.repository.SportRepository
import javax.inject.Inject

class SportRepositoryImpl @Inject constructor(
    private val sportDataSource: SportDataSource
) : SportRepository{
    override suspend fun getSport(query : String): Result<SportVO> {
        return Result.runCatching {
            sportDataSource.getSport(query).getOrThrow()
        }
    }
}
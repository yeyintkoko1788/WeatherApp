package com.example.weatherapp.data.datasource.network

import com.example.weatherapp.domain.model.sport.SportVO

interface SportDataSource {
    suspend fun getSport(query: String) : Result<SportVO>
}
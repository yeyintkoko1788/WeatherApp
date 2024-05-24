package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.sport.SportVO

interface SportRepository {
    suspend fun getSport(query: String) : Result<SportVO>
}
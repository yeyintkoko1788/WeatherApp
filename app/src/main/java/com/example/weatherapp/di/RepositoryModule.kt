package com.example.weatherapp.di

import com.example.weatherapp.data.datasource.network.SportDataSource
import com.example.weatherapp.data.repository.SportRepositoryImpl
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.SportRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.network.datasource.SportDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl : WeatherRepositoryImpl
    ) : WeatherRepository

    @Singleton
    @Binds
    abstract fun bindSportRepository(sportRepositoryImpl: SportRepositoryImpl) : SportRepository
}
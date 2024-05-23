package com.example.weatherapp.di

import com.example.weatherapp.data.datasource.network.WeatherDataSource
import com.example.weatherapp.network.datasource.WeatherDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Singleton
    @Binds
    abstract fun bindWeatherNetworkDataSource(weatherDataSourceImpl: WeatherDataSourceImpl) : WeatherDataSource
}
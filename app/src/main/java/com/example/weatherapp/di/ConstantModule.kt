package com.example.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConstantModule {
    @Provides
    @Singleton
    @Named("weather_key")
    fun providesWeatherKey() : String = "b0b3722fe71245d2b2e53841242305"
}
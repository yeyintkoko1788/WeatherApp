package com.example.weatherapp.di

import android.app.Application
import android.content.Context
import com.example.weatherapp.domain.exception.FlowGenericErrorMessageFactory
import com.example.weatherapp.view.exception.FlowGenericErrorMessageFactoryImpl
import com.github.davidepanidev.kotlinextensions.utils.dispatchers.DefaultDispatcherProvider
import com.github.davidepanidev.kotlinextensions.utils.dispatchers.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun flowGenericErrorMessageFactory(flowGenericErrorMessageFactory : FlowGenericErrorMessageFactoryImpl) : FlowGenericErrorMessageFactory = flowGenericErrorMessageFactory

    @Provides
    @Singleton
    fun providerDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }

    @Provides
    @Singleton
    fun providesContext(application : Application) : Context = application.applicationContext!!
}
package com.example.weatherapp.di

import androidx.annotation.NonNull
import com.example.weatherapp.network.service.TestService
import com.example.weatherapp.network.showLogI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @NonNull
    @Provides
    fun provideShortsService(@Named("flowAuthenticatedBuilder") retrofitBuilder : Retrofit.Builder) : TestService {
        return retrofitBuilder.build().create(TestService::class.java)
    }

    @Singleton
    @NonNull
    @Named("flowAuthenticatedBuilder")
    @Provides
    fun getFlowAuthenticatedBuilder(@Named("flow_okhttp") httpClientBuilder : OkHttpClient.Builder, @Named("primary") retrofitBuilder : Retrofit.Builder) : Retrofit.Builder {
        val interceptor : Interceptor =
            AuthenticationInterceptor()
        if (!httpClientBuilder.interceptors().contains(interceptor)) {
            httpClientBuilder.addInterceptor(interceptor)
        }
        return retrofitBuilder.client(httpClientBuilder.build())

    }
}
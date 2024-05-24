package com.example.weatherapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.weatherapp.network.component.MyPreference
import com.google.firebase.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesSharePrefUtils(context : Context) =
        MyPreference(context)

    @Module
    @InstallIn(SingletonComponent::class)
    object Providers {

        /**
         * This okHttpClientBuilder is for Flow
         * **/
        @Singleton
        @Provides
        @Named("flow_okhttp")
        fun providesFlowOkHttpClientBuilder(context: Context): OkHttpClient.Builder {
            return OkHttpClient.Builder().apply {
                val loggerInterceptor = HttpLoggingInterceptor().apply {
                    level = when (BuildConfig.DEBUG) {
                        true -> HttpLoggingInterceptor.Level.HEADERS
                        false -> HttpLoggingInterceptor.Level.NONE
                    }
                }
                addInterceptor(loggerInterceptor)
//                .connectionSpecs(Collections.singletonList(spec))
//                    .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS , ConnectionSpec.CLEARTEXT , spec))
                    .addInterceptor(
                        ChuckerInterceptor.Builder(
                            context
                        ).build()
                    )
                    .readTimeout(8, TimeUnit.SECONDS)
                    .writeTimeout(8, TimeUnit.SECONDS)
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .cache(null)
            }
        }
    }

    @Suppress("DEPRECATION")
    @Provides
    @Named("primary")
    fun providesPrimaryRetrofitBuilder(gson : Gson) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create())
    }

    @Provides
    @Singleton
    fun gson() : Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setLenient()
        .create()
}
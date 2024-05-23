package com.example.weatherapp.di

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class AuthenticationInterceptor() : Interceptor {
    private var customTag : String? = null

    private var customName : String? = null


    fun setCustom(customName : String? , customTag : String?) {
        this.customTag = customTag
        this.customName = customName
    }


    @Throws(IOException::class)
    override fun intercept(chain : Interceptor.Chain) : Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Accept" , "application/json")

        val request = builder.build()
        return chain.proceed(request)
    }

}

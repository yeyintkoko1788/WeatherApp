package com.example.weatherapp.domain.exception

import com.example.weatherapp.domain.model.FlowReturnResult

interface FlowGenericErrorMessageFactory {
    fun getErrorMessage(throwable : Throwable) : String

    fun getErrorMessage(throwable : Throwable , defaultText : Int) : String

    fun <T> getError(throwable : Throwable , defaultText : Int) : FlowReturnResult<T>
}
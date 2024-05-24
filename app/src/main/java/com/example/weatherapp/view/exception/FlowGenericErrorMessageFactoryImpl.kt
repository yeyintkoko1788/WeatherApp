package com.example.weatherapp.view.exception

import android.content.Context
import com.example.weatherapp.R
import com.example.weatherapp.domain.exception.FlowGenericErrorMessageFactory
import com.example.weatherapp.domain.model.FlowReturnResult
import com.example.weatherapp.network.showLogE
import com.google.firebase.BuildConfig
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject


class FlowGenericErrorMessageFactoryImpl @Inject constructor(
    private val context : Context
) : FlowGenericErrorMessageFactory {
    override fun getErrorMessage(throwable : Throwable) : String {
        return mapErrorToUiMessage(throwable)
    }

    override fun getErrorMessage(throwable : Throwable , defaultText : Int) : String {
        return mapErrorToUiMessage(throwable , defaultText)
    }

    override fun <T> getError(throwable : Throwable , defaultText : Int) : FlowReturnResult<T> {
        return when (throwable) {
            is SocketTimeoutException  -> {
                FlowReturnResult.SocketTimeOutResult
            }

            is UnknownHostException    -> {
                FlowReturnResult.NetworkErrorResult
            }

            is SocketException         -> {
                FlowReturnResult.NetworkErrorResult
            }

            is IOException             -> {
                FlowReturnResult.IOExceptionResult
            }

            is retrofit2.HttpException -> {
                when (throwable.code()) {
                    301  -> FlowReturnResult.NewVersion
                    401  -> FlowReturnResult.SessionExpired
                    402  -> FlowReturnResult.PaymentOverdue
                    503  -> FlowReturnResult.ServerMaintenance
                    else -> {
                        val responseBody =
                            getJsonObjectFromCharSequence(parseMessageFromErrorBody(throwable.response()
                                ?.errorBody()))
                        var errorMessage = ""
                        errorMessage = if (responseBody != null) {
                            try {
                                if (responseBody.isNull("message"))
                                    responseBody["error"].toString()
                                else
                                    responseBody["message"].toString()
                            }catch (e : Exception){
                                try {
                                    context.getString(defaultText)
                                } catch (e : Exception) {
                                    "There has been an error while retrieving data. Please try again later."
                                }
                            }

                        } else {
                            try {
                                context.getString(defaultText)
                            } catch (e : Exception) {
                                "There has been an error while retrieving data. Please try again later."
                            }
                        }
                        showLogE("FLOW", errorMessage)
                        FlowReturnResult.ErrorResult(errorMessage)
                    }
                }
//
            }

            else                       -> if (BuildConfig.DEBUG) {
                FlowReturnResult.ErrorResult(throwable.toString())
            } else {
                try {
                    FlowReturnResult.ErrorResult(context.getString(defaultText))
                } catch (e : Exception) {
                    FlowReturnResult.ErrorResult("There has been an error while retrieving data. Please try again later.")
                }
            }
        }
    }

    fun mapErrorToUiMessage(error : Throwable , defaultText : Int = 0) : String {

        return when (error) {
            is SocketTimeoutException  -> "The service is temporarily unavailable. Please try again later."
            is UnknownHostException ,
            is SocketException ,
            is IOException             -> {
                "You appear to be offline. Please, check your internet connection and retry."
            }

            is retrofit2.HttpException -> {
                val responseBody =
                    getJsonObjectFromCharSequence(parseMessageFromErrorBody(error.response()
                        ?.errorBody()))

                if (responseBody != null) {
                    try {
                        if (responseBody.isNull("message")){
                            val error = responseBody["error"]
                            val gson = Gson()

                            val response = gson.fromJson(error.toString(), ErrorResponse::class.java)
                            response.message
                        }
                        else
                            responseBody["message"].toString()
                    }catch (e : Exception){
                        try {
                            context.getString(defaultText)
                        } catch (e : Exception) {
                            "There has been an error while retrieving data. Please try again later."
                        }
                    }
                } else {
                    try {
                        context.getString(defaultText)
                    } catch (e : Exception) {
                        "There has been an error while retrieving data. Please try again later."
                    }
                }
            }

            else                       -> if (BuildConfig.DEBUG) {
                error.toString()
            } else {
                try {
                    context.getString(defaultText)
                } catch (e : Exception) {
                    "There has been an error while retrieving data. Please try again later."
                }
            }
        }
    }

    private fun parseMessageFromErrorBody(errorBody : ResponseBody?) : CharSequence {

        if (errorBody == null) {
            return context.getString(R.string.error_generic)
        }

        val errorBodyInString =
            try {
                errorBody.string()
            } catch (e : Exception) {
                showLogE("e occurred at parsing error body string " , e)
                ""
            }

        showLogE("error body in string : $errorBodyInString")

        return errorBodyInString
    }

    fun getJsonObjectFromCharSequence(charSequence : CharSequence) : JSONObject? {
        return try {
            // Convert CharSequence to String
            val jsonString = charSequence.toString()

            // Parse the String as a JSON object
            JSONObject(jsonString)
        } catch (e : JSONException) {
            e.printStackTrace()
            null
        }
    }
}
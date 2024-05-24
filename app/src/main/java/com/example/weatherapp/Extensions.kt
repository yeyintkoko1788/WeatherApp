package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.content.res.Resources

fun Intent.navigate(context : Context) {
    context.startActivity(this)
}

fun getScreenWidth(context : Context , percentage : Double) : Int {
    val displayMetrics = Resources.getSystem().displayMetrics
    return (displayMetrics.widthPixels * percentage).toInt()
}
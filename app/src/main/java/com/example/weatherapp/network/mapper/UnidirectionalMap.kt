package com.example.weatherapp.network.mapper

interface UnidirectionalMap<S, T> {
    fun map(data: S): T
}
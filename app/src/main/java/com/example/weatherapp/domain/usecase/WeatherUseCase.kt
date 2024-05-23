package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.weather.WeatherVO
import com.example.weatherapp.domain.repository.WeatherRepository
import fr.haan.resultat.Resultat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<Resultat<WeatherVO>> = flow {
        emit(Resultat.loading())
        weatherRepository.getWeather()
            .onSuccess {
                emit(Resultat.success(it))
            }
            .onFailure {
                throw it
            }
    }
}
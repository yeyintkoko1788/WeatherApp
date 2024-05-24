package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.astronomy.AstronomyVO
import com.example.weatherapp.domain.repository.WeatherRepository
import fr.haan.resultat.Resultat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AstronomyUseCase@Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    data class Params(
        var query : String = "",
        var dt : String = ""
    )

    operator fun invoke(params: Params) : Flow<Resultat<AstronomyVO>> = flow {
        emit(Resultat.loading())
        weatherRepository.getAstronomy(params.query, params.dt)
            .onSuccess {
                emit(Resultat.success(it))
            }
            .onFailure {
                throw it
            }
    }
}
package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.domain.repository.WeatherRepository
import fr.haan.resultat.Resultat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    data class Params(
        var query : String = "",
    )

    operator fun invoke(params: Params) : Flow<Resultat<List<CityVO>>> = flow {
        emit(Resultat.loading())
        weatherRepository.getSearchResult(params.query)
            .onSuccess {
                emit(Resultat.success(it))
            }
            .onFailure {
                throw it
            }
    }
}
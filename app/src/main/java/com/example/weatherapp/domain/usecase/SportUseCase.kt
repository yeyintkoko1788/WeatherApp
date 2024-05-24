package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.domain.model.sport.SportVO
import com.example.weatherapp.domain.repository.SportRepository
import fr.haan.resultat.Resultat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SportUseCase @Inject constructor(
    private val sportRepository: SportRepository
) {

    data class Params(
        var query : String = "",
    )

    operator fun invoke(params: Params) : Flow<Resultat<SportVO>> = flow {
        emit(Resultat.loading())
        sportRepository.getSport(params.query)
            .onSuccess {
                emit(Resultat.success(it))
            }
            .onFailure {
                throw it
            }
    }
}
package com.example.weatherapp.view.ui.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.FlowReturnResult
import com.example.weatherapp.domain.model.sport.SportVO
import com.example.weatherapp.domain.usecase.SportUseCase
import com.example.weatherapp.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.haan.resultat.Resultat
import fr.haan.resultat.onFailure
import fr.haan.resultat.onLoading
import fr.haan.resultat.onSuccess
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class SportViewModel @Inject constructor(
    private val sportUseCase: SportUseCase
): BaseViewModel() {

    val sportFlow : MutableLiveData<FlowReturnResult<SportVO>> = MutableLiveData()

    fun getSport(query: String){
        sportUseCase(SportUseCase.Params(query)).onEach {
            handleSportData(it)
        }.catch {
            handleSportData(Resultat.failure(it))
        }.launchIn(viewModelScope)
    }

    private fun handleSportData(resultat : Resultat<SportVO>){
        resultat.onLoading {
            sportFlow.postValue(FlowReturnResult.LoadingRelsult())
        }
            .onSuccess {
                sportFlow.postValue(FlowReturnResult.PositiveResult(it))
            }
            .onFailure {
                sportFlow.postValue(FlowReturnResult.ErrorResult(flowGenericErrorMessageFactory.getErrorMessage(it)))
            }
    }
}
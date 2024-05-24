package com.example.weatherapp.view.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.model.FlowReturnResult
import com.example.weatherapp.domain.model.city.CityVO
import com.example.weatherapp.domain.usecase.SearchUseCase
import com.example.weatherapp.view.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.haan.resultat.Resultat
import fr.haan.resultat.onFailure
import fr.haan.resultat.onLoading
import fr.haan.resultat.onSuccess
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
): BaseViewModel() {

    val searchFlow : MutableLiveData<FlowReturnResult<List<CityVO>>> =
        MutableLiveData()

    fun getSearchResult(query : String){
        searchUseCase(SearchUseCase.Params(query)).onEach {
            handleSearchData(it)
        }.catch {
            handleSearchData(Resultat.failure(it))
        }.launchIn(viewModelScope)
    }

    private fun handleSearchData(resultat : Resultat<List<CityVO>>){
        resultat.onLoading {
            searchFlow.postValue(FlowReturnResult.LoadingRelsult())
        }
            .onSuccess {
                searchFlow.postValue(FlowReturnResult.PositiveResult(it))
            }
            .onFailure {
                searchFlow.postValue(FlowReturnResult.ErrorResult(flowGenericErrorMessageFactory.getErrorMessage(it)))
            }
    }
}
package com.example.weatherapp.view.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.exception.FlowGenericErrorMessageFactory
import com.example.weatherapp.domain.model.FlowReturnResult
import com.example.weatherapp.domain.model.weather.WeatherVO
import com.example.weatherapp.domain.usecase.WeatherUseCase
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
class MainViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel(){
    @Inject
    lateinit var flowGenericErrorMessageFactory : FlowGenericErrorMessageFactory

    val liveEventFlow : MutableLiveData<FlowReturnResult<WeatherVO>> =
        MutableLiveData()

    public fun getWeather(){
        weatherUseCase().onEach {
            handleWeatherData(it)
        }.catch {
            handleWeatherData(Resultat.failure(it))
        }.launchIn(viewModelScope)
    }

    private fun handleWeatherData(resultat : Resultat<WeatherVO>){
        resultat.onLoading {
            liveEventFlow.postValue(FlowReturnResult.LoadingRelsult())
        }
            .onSuccess {
                liveEventFlow.postValue(FlowReturnResult.PositiveResult(it))
            }
            .onFailure {
                liveEventFlow.postValue(FlowReturnResult.ErrorResult(flowGenericErrorMessageFactory.getErrorMessage(it)))
            }
    }
}
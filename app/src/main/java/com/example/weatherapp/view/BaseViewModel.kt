package com.example.weatherapp.view

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.exception.FlowGenericErrorMessageFactory
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    @Inject
    lateinit var flowGenericErrorMessageFactory : FlowGenericErrorMessageFactory
}
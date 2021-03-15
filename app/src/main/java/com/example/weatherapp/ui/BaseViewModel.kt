package com.example.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.error.ErrorHandler
import com.example.weatherapp.domain.error.ErrorHandlerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel(
    errorHandler: ErrorHandler = ErrorHandlerImpl()
) : ViewModel(), ErrorHandler by errorHandler {

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        call: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context) {
        try {
            _isLoadingLiveData.value = true
            call()
        } catch (error: Throwable) {
            handleError(error)
        } finally {
            _isLoadingLiveData.value = false
        }
    }
}
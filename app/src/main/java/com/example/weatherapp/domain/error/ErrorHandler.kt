package com.example.weatherapp.domain.error

import androidx.lifecycle.LiveData

interface ErrorHandler {
    val errorWithMessageLiveData: LiveData<String>
    val notFoundErrorLiveData: LiveData<Unit>
    val networkErrorLiveData: LiveData<Unit>
    val unknownErrorLiveData: LiveData<Unit>

    fun handleError(error: Throwable)
}

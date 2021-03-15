package com.example.weatherapp.domain.error

import androidx.lifecycle.LiveData
import com.example.weatherapp.domain.exception.BadRequestErrorException
import com.example.weatherapp.domain.exception.InternalErrorException
import com.example.weatherapp.domain.exception.NotFountErrorException
import com.example.weatherapp.domain.exception.UnauthorizedErrorException
import com.example.weatherapp.util.SingleLiveEvent
import java.net.UnknownHostException

class ErrorHandlerImpl : ErrorHandler {

    private val _notFoundErrorLiveData = SingleLiveEvent<Unit>()
    override val notFoundErrorLiveData: LiveData<Unit> = _notFoundErrorLiveData

    private val _networkErrorLiveData = SingleLiveEvent<Unit>()
    override val networkErrorLiveData: LiveData<Unit> = _networkErrorLiveData

    private val _unknownErrorLiveData = SingleLiveEvent<Unit>()
    override val unknownErrorLiveData: LiveData<Unit> = _unknownErrorLiveData

    private val _errorWithMessageLiveData = SingleLiveEvent<String>()
    override val errorWithMessageLiveData: LiveData<String> = _errorWithMessageLiveData

    override fun handleError(error: Throwable) {
        when (error) {
            is NotFountErrorException -> _notFoundErrorLiveData.value = Unit
            is BadRequestErrorException -> _errorWithMessageLiveData.value = error.error.message!!
            is InternalErrorException -> _errorWithMessageLiveData.value = error.error.message!!
            is UnauthorizedErrorException -> _errorWithMessageLiveData.value = error.error.message!!
            is UnknownHostException -> _errorWithMessageLiveData.value = error.message
            else -> _unknownErrorLiveData.value = Unit
        }
    }
}

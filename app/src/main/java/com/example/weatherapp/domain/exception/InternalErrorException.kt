package com.example.weatherapp.domain.exception

import com.example.weatherapp.domain.model.ApiError

class InternalErrorException(val error: ApiError) : RuntimeException()

package com.example.weatherapp.domain.exception

import com.example.weatherapp.domain.model.ApiError

class BadRequestErrorException(val error: ApiError) : RuntimeException()

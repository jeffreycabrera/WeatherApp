package com.example.weatherapp.domain.exception

import com.example.weatherapp.domain.model.ApiError

class UnauthorizedErrorException(val error: ApiError) : RuntimeException()

package com.example.weatherapp.adapter

import com.example.weatherapp.domain.exception.BadRequestErrorException
import com.example.weatherapp.domain.exception.InternalErrorException
import com.example.weatherapp.domain.exception.NotFountErrorException
import com.example.weatherapp.domain.exception.UnauthorizedErrorException
import com.example.weatherapp.domain.model.ApiError
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import java.net.HttpURLConnection

class CallWithErrorHandling(
    private val delegate: Call<Any>
) : Call<Any> by delegate {
    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, t)
            }

            override fun onResponse(call: Call<Any>, response: retrofit2.Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    val gsonBuilder = GsonBuilder().create()

                    val error = response.errorBody()?.string()?.let {
                        try {
                            val adapter = gsonBuilder.getAdapter(ApiError::class.java)
                            adapter.fromJson(it) ?: ApiError(message = response.toString())
                        } catch (t: Throwable) {
                            ApiError(message = response.toString())
                        }
                    } ?: ApiError(message = response.toString())

                    when (response.code()) {
                        HttpURLConnection.HTTP_BAD_REQUEST -> callback.onFailure(
                            call,
                            BadRequestErrorException(error)
                        )
                        HttpURLConnection.HTTP_NOT_FOUND -> callback.onFailure(
                            call,
                            NotFountErrorException()
                        )
                        HttpURLConnection.HTTP_INTERNAL_ERROR -> callback.onFailure(
                            call,
                            InternalErrorException(error)
                        )
                        HttpURLConnection.HTTP_UNAUTHORIZED -> callback.onFailure(
                            call,
                            UnauthorizedErrorException(error)
                        )
                        else -> callback.onFailure(call, Throwable())
                    }
                }
            }
        })
    }

    override fun clone(): Call<Any> = CallWithErrorHandling(delegate.clone())
}

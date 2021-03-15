package com.example.weatherapp.adapter

import retrofit2.Call
import retrofit2.CallAdapter

class ErrorsCallAdapter(
    val delegate: CallAdapter<Any, Call<*>>
) : CallAdapter<Any, Call<*>> by delegate {
    override fun adapt(call: Call<Any>): Call<*> = delegate.adapt(CallWithErrorHandling(call))
}

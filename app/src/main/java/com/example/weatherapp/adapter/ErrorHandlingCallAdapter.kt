package com.example.weatherapp.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class ErrorHandlingCallAdapter : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, Call<*>> {
        val delegate = retrofit.nextCallAdapter(this, returnType, annotations)
        return ErrorsCallAdapter(
            delegate = delegate as CallAdapter<Any, Call<*>>
        )
    }
}

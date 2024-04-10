package com.example.melz.network

sealed interface ApiResponse<T> {

    data class Success<T>(val data: T) : ApiResponse<T>
    data class Failure<T>(val exception: Exception) : ApiResponse<T>

    fun onSuccess(block : (T) -> Unit):ApiResponse<T>{
        if(this is Success)
            block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit):ApiResponse<T>{
        if(this is Failure)
            block(exception)
        return this
    }
}
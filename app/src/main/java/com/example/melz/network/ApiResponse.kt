package com.example.melz.network

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ApiResponse")
sealed class ApiResponse<out T : Any> {
    @Serializable
    @SerialName("Success")
    data class Success<out T : Any>(
        val data: T?
    ) : ApiResponse<T>()

    @Serializable
    @SerialName("Error")
    data class Error(
        @Contextual
        val exception: Throwable? = null,
        val responseCode: Int = -1
    ) : ApiResponse<Nothing>()

    fun handleResult(onSuccess: ((responseData: T?) -> Unit)?, onError: ((error: Error) -> Unit)?) {
        when (this) {
            is Success -> {
                onSuccess?.invoke(this.data)
            }
            is Error -> {
                onError?.invoke(this)
            }
        }
    }
}

@Serializable
data class ErrorResponse(
    var errorCode: Int = 1,
    val errorMessage: String = "Something went wrong"
)
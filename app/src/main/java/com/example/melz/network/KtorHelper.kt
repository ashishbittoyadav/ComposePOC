package com.example.melz.network

import android.util.Log
import com.example.melz.model.Category
import com.example.melz.model.MealCategory
import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.statuspages.*
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.Exception

private const val NETWORK_TIME_OUT = 6_000L
val httpClientAndroid = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    useAlternativeNames = true
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                }
            )
        }

        install(HttpTimeout) {
            requestTimeoutMillis = NETWORK_TIME_OUT
            connectTimeoutMillis = NETWORK_TIME_OUT
            socketTimeoutMillis = NETWORK_TIME_OUT
        }

        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }

//        install(ResponseObserver) {
//            onResponse { response ->
//                if(response.status.value in 200..299){
//                    apiCallback.onResponse(response.call,response)
//                }else{
//                    apiCallback.onFailure(response.call, Exception(response.status.value.toString()+":"+response.status.description))
//                }
//                Log.d("HTTP status:", "${response.status.value}")
//            }
//        }


        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

        HttpResponseValidator {
            validateResponse { response ->
                Log.d("KtorHelper.TAG", "HttpResponseValidator: fail")
                val statusCode = response.status.value
                if(statusCode !in 200..299) {
                    Log.d("KtorHelper.TAG", "HttpResponseValidator: fail")
                    ApiResponse.Error(Throwable(response.status.description),response.status.value)
//                    throw ClientRequestException(response, response.status.description)
                }else{
                    ApiResponse.Success(response.body())
                }
            }
        }
    }

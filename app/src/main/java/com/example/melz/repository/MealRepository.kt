package com.example.melz.repository

import android.util.Log
import com.example.melz.model.MealCategory
import com.example.melz.network.httpClientAndroid
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MealRepository {

    suspend fun getMealCategory() {
        CoroutineScope(IO).launch {
            httpClientAndroid.get("https://www.themealdb.com/api/json/v1/1/categories.php")
//            }).httpClientAndroid.get("https://www.themealdb.com/api/json/v1/1")
                .call
                .body<MealCategory>().let {
                    Log.d("ComposeBody.TAG", "Greeting: $it")
                }
        }
    }

}
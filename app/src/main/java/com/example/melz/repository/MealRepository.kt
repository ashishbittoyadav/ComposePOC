package com.example.melz.repository

import com.example.melz.model.Meal
import com.example.melz.model.MealCategory
import com.example.melz.network.NetworkCallHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealRepository {

    suspend fun getMealCategory(onSuccess : (data:MealCategory)->Unit,onFailure: (exception:Exception)->Unit) {
        CoroutineScope(IO).launch {
            NetworkCallHelper.getCategoryResponse().onFailure {
                onFailure(it)
            }.onSuccess {
                onSuccess(it)
            }
        }
    }

}
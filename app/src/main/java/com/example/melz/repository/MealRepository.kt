package com.example.melz.repository

import android.content.Context
import android.util.Log
import com.example.melz.model.Meal
import com.example.melz.model.MealCategory
import com.example.melz.network.NetworkCallHelper
import com.example.melz.roomdb.MealDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealRepository(context: Context) {

    private val TAG = "MealRepository.TAG"

    private val mealDao = MealDatabase.getInstance(context).mealDao

    suspend fun getMealCategory(onSuccess : (data:MealCategory)->Unit,onFailure: (exception:Exception)->Unit) {
        CoroutineScope(IO).launch {
            getCategoryFromLocal().let {
                if (it.isNotEmpty()){
                    onSuccess(MealCategory(it))
                }else{
                    NetworkCallHelper.getCategoryResponse().onFailure {
                        onFailure(it)
                    }.onSuccess {
                        this.launch {
                            mealDao.insertMealCategory(it.categories!!)
                        }
                        onSuccess(it)
                    }
                }
            }
        }
    }

    private suspend fun getCategoryFromLocal() = mealDao.getCategories()

    suspend fun getCategoryById(categoryId:String) = mealDao.getCategoryById(categoryId)

}
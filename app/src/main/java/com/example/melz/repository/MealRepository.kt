package com.example.melz.repository

import com.example.melz.model.MealCategory
import com.example.melz.network.NetworkCallHelper
import com.example.melz.roomdb.MealDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealRepository @Inject constructor() {

    private val TAG = "MealRepository.TAG"

    @Inject lateinit var mealDao : MealDao

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
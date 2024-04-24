package com.example.melz.network

import com.example.melz.model.MealCategory
import com.example.melz.model.Meals
import io.ktor.client.call.body
import io.ktor.client.request.get

object NetworkCallHelper {

    suspend fun getCategoryResponse(url:String="https://www.themealdb.com/api/json/v1/1/categories.php"):ApiResponse<MealCategory>{
        return safeCallApi {
            httpClientAndroid.get(url).body<MealCategory>()
        }
    }

    suspend fun getResponseWithParams(mealName:String="Rice"):ApiResponse<Meals>{
        return safeCallApi {
            httpClientAndroid.get("https://www.themealdb.com/api/json/v1/1/search.php?s=$mealName"){
                url{
                    parameters.append("s",mealName)
                }
            }.body<Meals>()
        }
    }

    suspend fun getResponseWithInitial(mealInitial:String="a"):ApiResponse<Meals>{
        return safeCallApi {
            httpClientAndroid.get("https://www.themealdb.com/api/json/v1/1/search.php?a=$mealInitial"){
//                url{
//                    parameters.append("s",mealInitial)
//                }
            }.body<Meals>()
        }
    }
}

private inline fun <T> safeCallApi(apiCall:()->T):ApiResponse<T>{
    return try {
        ApiResponse.Success(apiCall())
    }catch (e:Exception){
        ApiResponse.Failure(e)
    }
}
package com.example.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melz.model.Meal
import com.example.melz.model.MealCategory
import com.example.melz.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MealUiState(
    val mealCategory: Any?= null,
    val isLoading:Boolean=true,
    val error:String=""
)
class MealViewModel : ViewModel() {

    private val mealRepository = MealRepository()

    private val _responseState = MutableStateFlow(MealUiState())
    val responseState : StateFlow<MealUiState> = _responseState.asStateFlow()

    fun getCategory(){
        viewModelScope.launch {
            mealRepository.getMealCategory(
                onFailure =  {
                    _responseState.value = MealUiState(mealCategory = null,isLoading = false, error = it.message.toString())
                },
                onSuccess = {
                    _responseState.value = MealUiState(mealCategory = it,isLoading = false)
                }
            )
        }
    }

}
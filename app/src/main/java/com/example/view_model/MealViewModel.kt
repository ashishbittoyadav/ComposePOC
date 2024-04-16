package com.example.view_model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
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

    private val _responseState = MutableStateFlow(MealUiState())
    val responseState : StateFlow<MealUiState> = _responseState.asStateFlow()

//    companion object {
//
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(
//                modelClass: Class<T>,
//                extras: CreationExtras
//            ): T {
//                // Get the Application object from extras
//                val application = checkNotNull(extras[APPLICATION_KEY])
//                // Create a SavedStateHandle for this ViewModel from extras
//                val savedStateHandle = extras.createSavedStateHandle()
//
//                return MealViewModel(
//                    application = application
//                ) as T
//            }
//        }
//    }

    fun getCategory(context: Context){
        viewModelScope.launch {
            val mealRepository = MealRepository(context = context)
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

    fun getCategoryById(context: Context,categoryId:String){
        viewModelScope.launch {
            val mealRepository = MealRepository(context = context)
            mealRepository.getCategoryById(categoryId).let {
                _responseState.value = MealUiState(it,false,"")
            }
        }
    }

}
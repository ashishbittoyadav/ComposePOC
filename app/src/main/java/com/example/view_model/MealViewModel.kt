package com.example.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MealViewModel : ViewModel() {

    var isBottomSheetOpen = MutableStateFlow(false)

    fun toggleBottomSheet() {
        isBottomSheetOpen.value = !isBottomSheetOpen.value
    }

}
package com.example.melz.model

import kotlinx.serialization.Serializable

@Serializable
data class MealCategory(
    val categories: List<Category>?=null
)
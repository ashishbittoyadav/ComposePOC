package com.example.melz.model

import kotlinx.serialization.Serializable

@Serializable
data class Meals(
    val meals: List<Meal>
)
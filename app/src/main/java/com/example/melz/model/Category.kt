package com.example.melz.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val idCategory: String="",
    val strCategory: String="",
    val strCategoryDescription: String="",
    val strCategoryThumb: String=""
)
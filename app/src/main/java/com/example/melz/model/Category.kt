package com.example.melz.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val idCategory: String="",
    val strCategory: String="",
    val strCategoryDescription: String="",
    val strCategoryThumb: String=""
)
package com.example.melz.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.melz.model.Category

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMealCategory(category: List<Category>)

    @Query("select * from category")
    suspend fun getCategories():List<Category>

    @Query("select * from category where idCategory like :categoryId")
    suspend fun getCategoryById(categoryId:String):Category
}
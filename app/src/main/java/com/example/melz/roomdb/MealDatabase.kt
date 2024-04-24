package com.example.melz.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.melz.model.Category
import io.ktor.util.reflect.instanceOf

@Database(entities = [Category::class], version = 1, exportSchema = false)
abstract class MealDatabase : RoomDatabase(){
    abstract val mealDao : MealDao
//    companion object{
//        @Volatile
//        private var INSTANCE : MealDatabase?=null
//
//        fun getInstance(context: Context):MealDatabase{
//            synchronized(this){
//                var instance = INSTANCE
//                if (instance==null){
//                    instance = Room.databaseBuilder(context.applicationContext,
//                        MealDatabase::class.java,
//                        "meal_data_base")
//                        .build()
//                    INSTANCE = instance
//                }
//                return INSTANCE!!
//            }
//        }
//    }

}
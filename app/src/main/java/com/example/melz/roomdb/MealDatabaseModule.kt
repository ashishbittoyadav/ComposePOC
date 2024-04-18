package com.example.melz.roomdb

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MealDatabaseModule {

    @Provides
    fun providesMealDao(mealDatabase: MealDatabase):MealDao{
        return mealDatabase.mealDao
    }

    @Provides
    fun providesMealDatabase(@ApplicationContext context: Context):MealDatabase{
        return Room.databaseBuilder(context,
            MealDatabase::class.java,
            "meal_data_base")
            .build()
    }
}
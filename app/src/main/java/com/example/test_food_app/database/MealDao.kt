package com.example.test_food_app.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.food_app.data.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateMeal(meal: Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getSavedMeal():Flow<List<Meal>>

    @Query("SELECT * FROM mealInformation")
    fun getSavedMealTest():LiveData<List<Meal>>

}
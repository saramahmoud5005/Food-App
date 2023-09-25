package com.example.test_food_app.repo

import com.example.food_app.data.Meal
import com.example.food_app.data.MealList
import com.example.food_app.network.MealApi
import com.example.test_food_app.database.MealDatabase
import retrofit2.Response
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val mealApi: MealApi,
    private val mealDatabase: MealDatabase
    ) {

    private val database = mealDatabase.mealDao()
    val getSavedMeal = database.getSavedMeal()

    suspend fun insertOrUpdateMeal(meal: Meal){
        database.insertOrUpdateMeal(meal)
    }

    suspend fun deleteMeal(meal: Meal){
        database.deleteMeal(meal)
    }

    suspend fun getMealInfo(mealId:String):Response<MealList>{
        return mealApi.getMealInfo(mealId)
    }

}
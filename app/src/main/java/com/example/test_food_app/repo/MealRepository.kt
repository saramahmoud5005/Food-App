package com.example.test_food_app.repo

import com.example.food_app.data.MealList
import com.example.food_app.network.MealApi
import retrofit2.Response
import javax.inject.Inject

class MealRepository @Inject constructor(private val mealApi: MealApi) {

    suspend fun getMealInfo(mealId:String):Response<MealList>{
        return mealApi.getMealInfo(mealId)
    }

}
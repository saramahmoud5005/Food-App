package com.example.test_food_app.repo

import com.example.food_app.network.MealApi
import com.example.test_food_app.data.PopularMealList
import retrofit2.Response
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val mealApi: MealApi
) {

    suspend fun getCategory(category:String): Response<PopularMealList>{
        val response = mealApi.getcategory(category)
        return response
    }
}
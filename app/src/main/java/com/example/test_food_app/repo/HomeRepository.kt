package com.example.food_app.repo

import android.util.Log
import com.example.food_app.data.MealList
import com.example.food_app.network.MealApi
import com.example.test_food_app.data.CategoriesList
import retrofit2.Response
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val mealApi: MealApi
) {

    suspend fun getPopularMeals(p1:String):Response<List<MealList>>
    {
        return  mealApi.getPopularMeals(p1)
    }
    suspend fun getCategories():Response<CategoriesList>
    {
        return  mealApi.getCategories()
    }
    suspend fun getRandomMeal():Response<MealList>
    {

        val response = mealApi.getRandomMeal()
        if(response.isSuccessful)
        {
            Log.d("HomeRepo","Success to connect to random meal : response code "+response.code().toString())
        }
        else
        {
            Log.d("HomeRepo","Fail to connect to random meal : response code "+response.code().toString())
        }
        return response
    }
}
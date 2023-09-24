package com.example.food_app.network

import com.example.food_app.data.MealList
import com.example.test_food_app.data.CategoriesList
import com.example.test_food_app.data.PopularMealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomMeal(): Response<MealList>

    @GET("/api/json/v1/1/filter.php")
    suspend fun getPopularMeals(@Query("c")category:String):Response<PopularMealList>

    @GET("/api/json/v1/1/categories.php")
    suspend fun getCategories():Response<CategoriesList>
}
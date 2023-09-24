package com.example.food_app.network

import com.example.food_app.data.MealList
import retrofit2.Response
import retrofit2.http.GET

interface MealApi {

    @GET("/api/json/v1/1/random.php")
    suspend fun getRandomMeal(): Response<MealList>
}
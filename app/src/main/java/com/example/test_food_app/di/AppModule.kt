package com.example.food_app.di

import android.app.Application
import androidx.room.Room
import com.example.food_app.network.MealApi
import com.example.test_food_app.database.MealDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(okHttpClient: OkHttpClient): MealApi =
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MealApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app:Application):MealDatabase = Room.databaseBuilder(app,MealDatabase::class.java,"meal.db").build()


    @Provides
    @Singleton
    fun proviedOKHttp():OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(30,TimeUnit.SECONDS)
            .connectTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .build()
}
package com.example.test_food_app.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.food_app.data.Meal
import com.example.test_food_app.database.MealDao
import com.example.test_food_app.database.MealDatabase
import com.example.test_food_app.getOrAwaitValue
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MealDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MealDatabase
    private lateinit var dao: MealDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MealDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.mealDao()
    }

    @After
    fun finish(){
        database.close()
    }

    @Test
    fun insertMeal() = runBlocking{
        val meal = Meal(1,"MealCategory","MealInstruction",
            "MealName","MealPhoto",
            "MealThumb","MealSource",
        "MealTag","MealYoutube")

        dao.insertOrUpdateMeal(meal)

        val allMeals = dao.getSavedMealTest().getOrAwaitValue()

        assertThat(allMeals).contains(meal)
    }

    @Test
    fun deleteMeal() = runBlocking{
        val meal = Meal(1,"MealCategory","MealInstruction",
            "MealName","MealPhoto",
            "MealThumb","MealSource",
            "MealTag","MealYoutube")

        dao.deleteMeal(meal)

        val allMeals = dao.getSavedMealTest().getOrAwaitValue()

        assertThat(allMeals).doesNotContain(meal)
    }
}
package com.example.food_app.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_app.data.Meal
import com.example.food_app.repo.HomeRepository
import com.example.test_food_app.data.Category
import com.example.test_food_app.data.PopularMeal
import com.example.test_food_app.data.PopularMealList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) :ViewModel() {

    private val _getRandomMealLiveData = MutableLiveData<Meal>()
    val getRandomMealLiveData : LiveData<Meal> = _getRandomMealLiveData

    //save data when rotate
    private var saveStateRandomMeal:Meal?=null
    fun getRandomMeal(){
        /*saveStateRandomMeal?.let{ randomMeal->
            _getRandomMealLiveData.postValue(randomMeal)
            return@let
        }*/
        viewModelScope.launch {
            val response= homeRepository.getRandomMeal()
            response.body()!!.meals.let {
                _getRandomMealLiveData.postValue(it[0])
            }
            //saveStateRandomMeal = response.body()!!.meals[0]
        }
    }

    private val _getPopularMealsLiveData =MutableLiveData<List<PopularMeal>>()
    val getPopularMealsLiveData : LiveData<List<PopularMeal>> = _getPopularMealsLiveData
    fun getPopularMeals(){
        viewModelScope.launch {
            val response =homeRepository.getPopularMeals("Seafood")
            response.body()?.meals.let{
                _getPopularMealsLiveData.postValue(it)
            }
        }
    }

    private val _getCategoriesStateFlow =MutableStateFlow<List<Category>>(emptyList())
    val getCategoriesStateFlow : MutableStateFlow<List<Category>> = _getCategoriesStateFlow
    fun getCategories(){
        viewModelScope.launch {
            val response =homeRepository.getCategories()
            response.body()?.categories.let{ data->
                _getCategoriesStateFlow.value =data!!
            }
        }
    }

}
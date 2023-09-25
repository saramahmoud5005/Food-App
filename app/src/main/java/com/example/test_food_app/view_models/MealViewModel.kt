package com.example.test_food_app.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_app.data.Meal
import com.example.test_food_app.repo.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(private val mealRepository: MealRepository) :ViewModel() {

    private val _getMealInfoLiveData = MutableLiveData<Meal>()
    val getMealInfoLiveData : LiveData<Meal> =_getMealInfoLiveData

    fun getMealInfo(mealId:String){
        viewModelScope.launch {
            try {
                val response = mealRepository.getMealInfo(mealId)
                /*response.body()?.meals.let{

                }*/
                _getMealInfoLiveData.postValue(response.body()!!.meals[0])
            }catch (t:Throwable){
                Log.d("MealViewModel",t.message.toString())
            }
        }
    }

    fun insertOrUpdateMeal(meal: Meal)=viewModelScope.launch {
        mealRepository.insertOrUpdateMeal(meal)
    }

    fun deleteMeal(meal: Meal)=viewModelScope.launch {
        mealRepository.deleteMeal(meal)
    }

    fun getSavedMeal()=mealRepository.getSavedMeal
}
package com.example.food_app.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food_app.data.Meal
import com.example.food_app.repo.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) :ViewModel() {

    private val _getRandomMealLiveData = MutableLiveData<Meal>()
    val getRandomMealLiveData : LiveData<Meal> = _getRandomMealLiveData
    fun getRandomMeal(){
        viewModelScope.launch {
            val response= homeRepository.getRandomMeal()
            response.body()!!.meals.let {
                _getRandomMealLiveData.postValue(it[0])
            }
        }
    }
}
package com.example.test_food_app.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_food_app.data.PopularMeal
import com.example.test_food_app.repo.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) :ViewModel() {

    private var _categorystateflow = MutableStateFlow<List<PopularMeal>>(emptyList())
    var categorystateFlow :StateFlow<List<PopularMeal>> = _categorystateflow

    fun getCategory(category:String){
        viewModelScope.launch {
            try {
                val response =categoryRepository.getCategory(category)
                _categorystateflow.emit( response.body()!!.meals )
            }catch (t:Throwable){

            }
        }
    }
}
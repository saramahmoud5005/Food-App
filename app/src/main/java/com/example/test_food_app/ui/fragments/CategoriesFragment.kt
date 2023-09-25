package com.example.test_food_app.ui.fragments

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import com.example.test_food_app.R
import com.example.test_food_app.databinding.FragmentCategoryBinding
import com.example.test_food_app.view_models.CategoryViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryName:String
    private val categoryViewModel : CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataByBundle()
        getCategoryInfo()
    }

    private fun getDataByBundle(){
        val data =arguments
        if(data!=null) {
            categoryName = data.getString("categoryName").toString()
        }
    }
    private fun getCategoryInfo(){
        lifecycleScope.launchWhenStarted {
            categoryViewModel.getCategory(categoryName)
            categoryViewModel.categorystateFlow.collect{data->
                for(i in data)
                    Log.d("CategoriesFragment",i.strMeal)
            }
        }
    }

}
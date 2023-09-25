package com.example.test_food_app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_food_app.adapters.CategoryFragmentAdapter
import com.example.test_food_app.databinding.FragmentCategoryBinding
import com.example.test_food_app.view_models.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment :Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryName:String
    private val categoryViewModel : CategoryViewModel by viewModels()
    private lateinit var categoryFragmentAdapter: CategoryFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryFragmentAdapter = CategoryFragmentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataByBundle()
        getCategoryInfo()
        setCategoryRecView()
    }

    private fun getDataByBundle(){
        val data =arguments
        if(data!=null) {
            categoryName = data.getString("categoryName").toString()
            Log.d("CategoryFragment",categoryName)
        }
    }
    private fun getCategoryInfo(){
        lifecycleScope.launchWhenStarted {
            categoryViewModel.getCategory(categoryName)
            categoryViewModel.categorystateFlow.collect{data->
                categoryFragmentAdapter.differ.submitList(data)
//                for(i in data)
//                    Log.d("CategoriesFragment",i.strMeal)
            }
        }
    }

    private fun setCategoryRecView(){
        binding.categoryFragmentRv.apply {
            layoutManager = GridLayoutManager(context,2, RecyclerView.VERTICAL,false)
            adapter = categoryFragmentAdapter
        }
    }
}
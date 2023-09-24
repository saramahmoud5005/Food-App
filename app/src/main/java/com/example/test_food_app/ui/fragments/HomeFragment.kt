package com.example.test_food_app.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.example.food_app.view_models.HomeViewModel
import com.example.test_food_app.adapters.CategoriesHomeAdapter
import com.example.test_food_app.adapters.PopularMealsAdapter
import com.example.test_food_app.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val homeViewModel : HomeViewModel by viewModels ()
    private lateinit var popularMealsAdapter:PopularMealsAdapter
    private lateinit var categoriesHomeAdapter:CategoriesHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularMealsAdapter = PopularMealsAdapter()
        categoriesHomeAdapter = CategoriesHomeAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeViewModel.getRandomMeal()
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRandomMeal()
        getPopularMeals()
        setUpPopularMealRecView()
        getCategories()
        setUpRecViewCategories()
    }
    private fun getPopularMeals(){
        homeViewModel.getPopularMeals()
        homeViewModel.getPopularMealsLiveData.observe(viewLifecycleOwner){data->
            //Log.d("TAG123",data.toString())
            popularMealsAdapter.differ.submitList(data)
        }
    }
    private fun setUpPopularMealRecView(){
        binding.overRv.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
            adapter = popularMealsAdapter
        }
    }
    private fun getRandomMeal(){
        homeViewModel.getRandomMealLiveData.observe(viewLifecycleOwner){data->
            Glide.with(this)
                .load(data.strMealThumb)
                .into(binding.randomImage)
        }
    }
    private fun getCategories(){
        homeViewModel.getCategories()
        lifecycleScope.launchWhenStarted {
            homeViewModel.getCategoriesStateFlow.collect{ data->
                Log.d("Categories Data",data.toString())
                categoriesHomeAdapter.differ.submitList(data)
            }
        }
    }
    private fun setUpRecViewCategories(){
        binding.categoriesRv.apply{
            layoutManager = GridLayoutManager(context,3,RecyclerView.VERTICAL,false)
            adapter = categoriesHomeAdapter
        }
    }

}
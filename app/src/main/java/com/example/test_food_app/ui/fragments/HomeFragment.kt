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
import com.bumptech.glide.Glide
import com.example.food_app.view_models.HomeViewModel
import com.example.test_food_app.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
     private val homeViewModel : HomeViewModel by viewModels ()

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
            }
        }
    }

}
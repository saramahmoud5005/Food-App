package com.example.test_food_app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app.data.Meal
import com.example.test_food_app.R
import com.example.test_food_app.adapters.FavoriteAdapter
import com.example.test_food_app.databinding.FavoriteRowBinding
import com.example.test_food_app.databinding.FragmentFavoritesBinding
import com.example.test_food_app.view_models.MealViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val mealViewModel:MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteAdapter = FavoriteAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFavoriteRecView()
        getSavedData()

        //todo
        onFavoriteMealClick()
    }

    private fun getSavedData(){
        lifecycleScope.launchWhenStarted {
              mealViewModel.getSavedMeal().collect{ savedData->
              favoriteAdapter.differ.submitList(savedData)
                  binding.countFavoriteMealsTv.text = "Favourite Meals: "+savedData.size.toString()
          }
        }
    }

    //todo
    private fun onFavoriteMealClick(){

        favoriteAdapter.onFavoriteMealClick = {data->
            lifecycleScope.launchWhenStarted {
                mealViewModel.deleteMeal(data)
            }
        }
    }

    private fun setFavoriteRecView(){
        binding.favoriteMealsRv.apply{
            layoutManager = GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
            adapter = favoriteAdapter
        }
    }
}
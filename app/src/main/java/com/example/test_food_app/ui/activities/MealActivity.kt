package com.example.test_food_app.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.food_app.data.Meal
import com.example.test_food_app.databinding.ActivityMealBinding
import com.example.test_food_app.view_models.MealViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MealActivity:AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealTitle:String
    private lateinit var mealImg:String
    private lateinit var virdoLink:String

    private val mealViewModel:MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInfo()
        mealViewModel.getMealInfo(mealId)
        setMealInfoInViews()

        binding.fltBtnFav.setOnClickListener{
            mealViewModel.insertOrUpdateMeal(saveMeal!!)
            lifecycleScope.launchWhenStarted {
                mealViewModel.getSavedMeal()!!.collect{ sMeal->
                    Log.d("MealActivity",sMeal.toString())
                }
            }

        }

    }


    private fun getMealInfo(){
       val intent =intent
        mealId = intent.getStringExtra("mealId").toString()
        mealTitle = intent.getStringExtra("mealTitle").toString()
        mealImg = intent.getStringExtra("mealImg").toString()

        Glide.with(applicationContext)
            .load(mealImg)
            .into(binding.mealImg)

        binding.collapsing.title=mealTitle
    }

    private var saveMeal:Meal?=null
    private fun setMealInfoInViews(){
        mealViewModel.getMealInfoLiveData.observe(this, Observer {data ->

            saveMeal = data
            binding.categoryTv.text = "Category: "+data.strCategory
            binding.locationTv.text = "Location: "+data.strArea
            binding.detailsInstructionsTv.text = data.strInstructions
            virdoLink = data.strYoutube
            binding.videoImg.setOnClickListener {
                val intent =Intent(Intent.ACTION_VIEW, Uri.parse(virdoLink))
                startActivity(intent)
            }
        })
    }
}
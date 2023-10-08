package com.example.test_food_app.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
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
    private var mealId:Int = 0
    private lateinit var mealTitle:String
    private lateinit var mealImg:String
    private lateinit var virdoLink:String

    private val mealViewModel:MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInfo()
        mealViewModel.getMealInfo(mealId.toString())
        setMealInfoInViews()

        binding.fltBtnFav.setOnClickListener{
            //
            if(saveMeal!=null&&mealId!=0)
            {
                mealViewModel.insertOrUpdateMeal(saveMeal!!)
                Toast.makeText(this,"Meal is saved in Fav",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this,"MealId${saveMeal?.idMeal?:0}mealId: $mealId",Toast.LENGTH_SHORT).show();
            }
        }
        binding.videoImg.setOnClickListener {
            if (virdoLink!=null && virdoLink!="") {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(virdoLink))
                startActivity(intent)
            }
        }

    }


    private fun getMealInfo(){
       val intent =intent
        mealTitle = intent.getStringExtra("mealTitle").toString()
        mealImg = intent.getStringExtra("mealImg").toString()
        mealId = intent.getIntExtra("mealId",0)

        Glide.with(applicationContext)
            .load(mealImg)
            .into(binding.mealImg)

        binding.collapsing.title=mealTitle
    }

    private var saveMeal:Meal?=null
    private fun setMealInfoInViews(){
        mealViewModel.getMealInfoLiveData.observe(this, Observer {data ->
            binding.categoryTv.text = "Category: "+data.strCategory
            binding.locationTv.text = "Location: "+data.strArea
            binding.detailsInstructionsTv.text = data.strInstructions
            virdoLink = data.strYoutube
            saveMeal= data

        })
    }
}
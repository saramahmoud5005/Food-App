package com.example.test_food_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app.data.Meal
import com.example.test_food_app.databinding.FavoriteRowBinding

class FavoriteAdapter :RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    //todo
    lateinit var onFavoriteMealClick :((Meal)->Unit)
    private val diffUtil = object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }

    }

    val differ =AsyncListDiffer(this,diffUtil)

    class ViewHolder(val binding :FavoriteRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder((FavoriteRowBinding.inflate(LayoutInflater.from(parent.context))))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(data.strMealThumb)
            .into(holder.binding.favoriteImg)

        holder.binding.mealTv.text = data.strMeal

        //todo
        holder.itemView.setOnClickListener{
            onFavoriteMealClick.invoke(data)
        }
    }

    override fun getItemCount() = differ.currentList.size
}
package com.example.test_food_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_food_app.data.PopularMeal

import com.example.test_food_app.databinding.FavoriteRowBinding

class CategoryFragmentAdapter:RecyclerView.Adapter<CategoryFragmentAdapter.ViewHolder>() {

    private val diffUtil = object :DiffUtil.ItemCallback<PopularMeal>(){
        override fun areItemsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {
            return oldItem== newItem
        }

    }
    val differ =AsyncListDiffer(this,diffUtil)
    class ViewHolder(val binding: FavoriteRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FavoriteRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data =differ.currentList[position]
        Glide.with(holder.itemView)
            .load(data.strMealThumb)
            .into(holder.binding.favoriteImg)

        holder.binding.mealTv.text=data.strMeal
    }

    override fun getItemCount() = differ.currentList.size
}
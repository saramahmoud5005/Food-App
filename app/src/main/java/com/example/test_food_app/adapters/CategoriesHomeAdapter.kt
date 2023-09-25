package com.example.test_food_app.adapters

import android.icu.util.ULocale.Category
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_food_app.databinding.CategoriesRowBinding

class CategoriesHomeAdapter:RecyclerView.Adapter<CategoriesHomeAdapter.ViewHolder>() {

    lateinit var onCategoryMealClick :((com.example.test_food_app.data.Category)->Unit)

    //lateinit var onCategoryMealClick:com.example.test_food_app.data.Category
    private val diffUtil = object :DiffUtil.ItemCallback<com.example.test_food_app.data.Category>(){
        override fun areItemsTheSame(
            oldItem: com.example.test_food_app.data.Category,
            newItem: com.example.test_food_app.data.Category
        ): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(
            oldItem: com.example.test_food_app.data.Category,
            newItem: com.example.test_food_app.data.Category
        ): Boolean {
           return oldItem == newItem
        }


    }

    val differ = AsyncListDiffer(this,diffUtil)

    class ViewHolder (val binding:CategoriesRowBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(CategoriesRowBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data =differ.currentList[position]
        Glide.with(holder.itemView)
            .load(data.strCategoryThumb)
            .into(holder.binding.categoriesImg)

        holder.binding.categoriesTv.text = data.strCategory

        holder.itemView.setOnClickListener{
            onCategoryMealClick.invoke(data)
        }
        //onCategoryMealClick=data
    }

    override fun getItemCount() = differ.currentList.size
}
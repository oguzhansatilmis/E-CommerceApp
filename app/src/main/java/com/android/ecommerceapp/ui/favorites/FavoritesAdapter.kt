package com.android.ecommerceapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.ecommerceapp.databinding.FavoritesitemBinding
import com.android.ecommerceapp.model.FavoritesEntity

class FavoritesAdapter(private val favoritesList: List<FavoritesEntity>):RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:FavoritesitemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.ViewHolder {
       val binding = FavoritesitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.ViewHolder, position: Int) {
        val favoritesItem =favoritesList[position]
        holder.binding.itemId.text = favoritesItem.title
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }
}
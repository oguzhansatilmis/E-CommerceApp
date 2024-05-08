package com.android.ecommerceapp.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.ecommerceapp.databinding.FavoritesitemBinding
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.ui.order.OrderAdapterDiffUtil
import com.android.ecommerceapp.util.loadUrl

class FavoritesAdapter(private val favoritesList: MutableList<Product>) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    var onSwipeDeleteItem: ((Product) -> Unit)? = null

    inner class ViewHolder(val binding: FavoritesitemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.ViewHolder {
        val binding =
            FavoritesitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoritesAdapter.ViewHolder, position: Int) {
        val favoritesItem = favoritesList[position]
        holder.binding.favoritesTitle.text = favoritesItem.title
        holder.binding.favoritesPrice.text = "${favoritesItem.price} $"
        holder.binding.favoritesImage.loadUrl(favoritesItem.image)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position1: Int) {


        if (position1 in favoritesList.indices) {
            onSwipeDeleteItem?.invoke(favoritesList[position1])
            favoritesList.removeAt(position1)
            updateList(favoritesList)
            notifyDataSetChanged()
            println("adapter if durumu ")
        } else {
            println("adapter else durumu ")
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateList(newList: MutableList<Product>) {
        val diffCallback = FavoritesAdapterDiffUtil(favoritesList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }
}
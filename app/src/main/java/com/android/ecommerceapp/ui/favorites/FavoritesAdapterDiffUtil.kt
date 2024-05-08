package com.android.ecommerceapp.ui.favorites

import androidx.recyclerview.widget.DiffUtil
import com.android.ecommerceapp.model.FavoritesEntity

class FavoritesAdapterDiffUtil(
    private val oldList:List<FavoritesEntity>,
    private val newList:List<FavoritesEntity>,
):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
     return oldList.size
    }

    override fun getNewListSize(): Int {
    return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return when{

            oldList[oldItemPosition].category != newList[newItemPosition].category ->{
                false
            }
            oldList[oldItemPosition].description != newList[newItemPosition].description ->{
                false
            }
            oldList[oldItemPosition].price != newList[newItemPosition].price ->{
                false
            }
            oldList[oldItemPosition].title != newList[newItemPosition].title ->{
                false
            }
            else ->{
                true
            }
        }
    }
}
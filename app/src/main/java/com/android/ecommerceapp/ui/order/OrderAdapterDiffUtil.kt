package com.android.ecommerceapp.ui.order

import androidx.recyclerview.widget.DiffUtil
import com.android.ecommerceapp.model.ExploreProduct

class OrderAdapterDiffUtil(
    private val oldOrderList: List<ExploreProduct>,
    private val newOrderList: List<ExploreProduct>,
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
      return oldOrderList.size
    }

    override fun getNewListSize(): Int {
       return newOrderList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldOrderList[oldItemPosition] == newOrderList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldOrderList[oldItemPosition].id != newOrderList[newItemPosition].id ->{
                false
            }
            oldOrderList[oldItemPosition].count != newOrderList[newItemPosition].count ->{
                false
            }
            oldOrderList[oldItemPosition].image!= newOrderList[newItemPosition].image ->{
                false
            }
            oldOrderList[oldItemPosition].price != newOrderList[newItemPosition].price ->{
                false
            }
            oldOrderList[oldItemPosition].title != newOrderList[newItemPosition].title ->{
                false
            }
            else ->{
                true
            }
        }
    }
}
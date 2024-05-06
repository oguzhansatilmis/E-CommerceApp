package com.android.ecommerceapp.ui.order

import androidx.recyclerview.widget.DiffUtil
import com.android.ecommerceapp.model.ExploreProduct

class OrderAdapterDiffUtil(
    private val oldOrderList: List<ExploreProduct>,
    private val newOrderList: List<ExploreProduct>,
) : DiffUtil.Callback() {
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
        return oldOrderList[oldItemPosition] == newOrderList[newItemPosition]
    }
}
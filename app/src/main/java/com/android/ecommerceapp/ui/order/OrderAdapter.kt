package com.android.ecommerceapp.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.ecommerceapp.databinding.OrderitemBinding

import com.android.ecommerceapp.model.ExploreProduct
import com.android.ecommerceapp.util.loadUrl

class OrderAdapter(private var orderList:List<ExploreProduct>?=null):RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    inner class ViewHolder( val binding: OrderitemBinding):RecyclerView.ViewHolder(binding.root)
    var listenerItemClick: ((ExploreProduct) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val binding = OrderitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = orderList?.get(position)
        holder.binding.orderImage.loadUrl(orderItem?.image.toString())
        holder.binding.orderTitle.text = orderItem?.title
        holder.binding.orderPrice.text = (orderItem?.price.toString() + " $")

        holder.binding.orderBasket.updateCount(orderItem!!.count)

        holder.binding.orderBasket.setOnClickIncrease {count->

            listenerItemClick?.let {
                orderItem.count = count
                it(orderItem)
            }
        }
        holder.binding.orderBasket.setOnClickDecrease {count->
            listenerItemClick?.let {
                orderItem.count = count
                it(orderItem)
            }
        }
        holder.binding.orderBasket.setOnClickTrash {count->

            listenerItemClick?.let {
                orderItem.count = count
                it(orderItem)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newOrderList: List<ExploreProduct>) {
        val nonZeroCountItems = newOrderList.filter { it.count != 0 }
        val oldList = orderList ?: emptyList()
        orderList = nonZeroCountItems
        val diffCallback = OrderAdapterDiffUtil(oldList, nonZeroCountItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
       return orderList?.size ?: 0
    }
}
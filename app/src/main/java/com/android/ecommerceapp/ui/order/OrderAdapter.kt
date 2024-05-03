package com.android.ecommerceapp.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.ecommerceapp.databinding.OrderitemBinding

import com.android.ecommerceapp.model.ExploreProduct
import com.android.ecommerceapp.util.customSetVisibility
import com.android.ecommerceapp.util.loadUrl

class OrderAdapter(private val orderList:List<ExploreProduct>):RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    inner class ViewHolder( val binding: OrderitemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val binding = OrderitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = orderList[position]
        holder.binding.orderImage.loadUrl(orderItem.image.toString())
        holder.binding.orderTitle.text = orderItem.title
        holder.binding.orderPrice.text = (orderItem.price.toString() + " $")

        holder.binding.orderBasket.updateCount(orderItem.count)

    }

    override fun getItemCount(): Int {
       return orderList.size
    }
}
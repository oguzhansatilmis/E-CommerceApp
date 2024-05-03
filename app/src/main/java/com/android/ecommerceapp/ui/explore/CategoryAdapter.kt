package com.android.ecommerceapp.ui.explore

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.android.ecommerceapp.R
import com.android.ecommerceapp.databinding.CategoryItemBinding
import com.android.ecommerceapp.model.ExploreProduct

import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.SelectedItems
import com.android.ecommerceapp.util.loadUrl

class CategoryAdapter(private val categoryList: List<ExploreProduct>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)


    var listenerItemClick: ((Int,Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryItem = categoryList[position]
        holder.binding.title.text = categoryItem.title
        holder.binding.price.text = (categoryItem.price.toString() + " $")


        categoryItem.image?.let {
            holder.binding.image.loadUrl(it)
        }

        println("count ${categoryItem.count}")
        holder.binding.basketView.updateCount(categoryItem.count)

        holder.binding.root.setOnClickListener {

//            val isSelected = holder.binding.cardview.isSelected
//            holder.binding.cardview.isSelected = !isSelected
//
//            val bundle = Bundle()
//            bundle.apply {
//                putString("itemId", categoryItem.id.toString())
//            }
//
//          Navigation.findNavController(it).navigate(R.id.action_exploreFragment_to_detailFragment,bundle)

        }
        holder.binding.basketView.setOnClickIncrease {count->

            listenerItemClick?.let {
                it(count,categoryItem.id!!)
            }

        }
        holder.binding.basketView.setOnClickDecrease {count->
            listenerItemClick?.let {
                it(count,categoryItem.id!!)
            }
        }
        holder.binding.basketView.setOnClickTrash {count->

            listenerItemClick?.let {
                it(count,categoryItem.id!!)
            }
        }
    }
    override fun getItemCount(): Int {
        return categoryList.size
    }
}
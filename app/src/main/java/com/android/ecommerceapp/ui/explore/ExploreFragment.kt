package com.android.ecommerceapp.ui.explore

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.ecommerceapp.MainActivity
import com.android.ecommerceapp.base.BaseFragment
import com.android.ecommerceapp.databinding.FragmentExploreBinding
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.E

@AndroidEntryPoint
class ExploreFragment :
    BaseFragment<FragmentExploreBinding, ExploreViewModel, MainActivity>(FragmentExploreBinding::inflate) {
    override val viewModel by viewModels<ExploreViewModel>(
        ownerProducer = {
           this
        }
    )
    private lateinit var adapter: CategoryAdapter
    private lateinit var electronicsAdapter: CategoryAdapter


    override fun onCreateFinished() {
        viewModel.getCategoryItems()
        binding.recyclerview.addItemDecoration(ItemDecoration())

        activity().hideProgress()
    }

    override fun initializeListeners() {


    }

    override fun observeEvents() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    activity().hideProgress()
                    it.data?.let { response ->
                        response.body()?.let { item ->
                            setupAdapter(item)
                        }
                    }
                }

                is Result.Loading -> {
                    activity().showProgress()
                }

                is Result.Error -> {
                    activity().hideProgress()
                }
            }
        }
    }

    private fun setupAdapter(categoryList: List<Product>) {
        adapter = CategoryAdapter(categoryList)
        electronicsAdapter = CategoryAdapter(categoryList)
        binding.apply {
            recyclerview.layoutManager = GridLayoutManager(requireContext(), 3)
            recyclerview.adapter = adapter

            electronicRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            electronicRecyclerview.adapter = electronicsAdapter
        }
        var totalPrice = 0.0
        adapter.listener = {

            viewModel.setData(it[0])

            it.forEach { price ->

                totalPrice += price.price
            }
            activity().setBasketPrice(totalPrice)
            totalPrice = 0.0
        }

    }

    companion object {

        val instance = ExploreFragment()
    }

    val deneme = "deneme"

    override fun onDestroyView() {
        super.onDestroyView()

        println("ExploreFragment onDestroyView")
    }
}


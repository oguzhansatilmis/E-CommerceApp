package com.android.ecommerceapp.ui.explore

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.ecommerceapp.activity.MainActivity
import com.android.ecommerceapp.activity.MainViewModel
import com.android.ecommerceapp.base.BaseFragment
import com.android.ecommerceapp.base.BaseSecondaryFragment
import com.android.ecommerceapp.databinding.FragmentExploreBinding
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment :
    BaseSecondaryFragment<FragmentExploreBinding, ExploreViewModel, MainViewModel, MainActivity>(
        FragmentExploreBinding::inflate
    ) {
    override val viewModel by viewModels<ExploreViewModel>()
    override val viewModel2 by activityViewModels<MainViewModel>()
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

            viewModel2.setSelectedItem(it)

            it.forEach { price ->

                totalPrice += price.price
            }
            activity().setBasketPrice(totalPrice)
            totalPrice = 0.0
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()

        println("ExploreFragment onDestroyView")
    }
}


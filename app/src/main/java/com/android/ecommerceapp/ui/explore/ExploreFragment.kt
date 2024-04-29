package com.android.ecommerceapp.ui.explore

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.ecommerceapp.MainActivity
import com.android.ecommerceapp.base.BaseFragment
import com.android.ecommerceapp.databinding.FragmentExploreBinding
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment :
    BaseFragment<FragmentExploreBinding, ExploreViewModel, MainActivity>(FragmentExploreBinding::inflate) {
    override val viewModel by viewModels<ExploreViewModel>()
    private lateinit var adapter: CategoryAdapter
    override fun onCreateFinished() {
        viewModel.getCategoryItems()
    }

    override fun initializeListeners() {
        binding.basketView.setOnClickDecrease {}
        binding.basketView.setOnClickIncrease {}
        binding.basketView.setOnClickTrash {}
    }

    override fun observeEvents() {
        viewModel.productsLiveData.observe(this) {
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
        binding.apply {
            recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerview.adapter = adapter
        }
    }
}
package com.android.ecommerceapp.ui.explore

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.ecommerceapp.activity.MainActivity
import com.android.ecommerceapp.activity.MainViewModel
import com.android.ecommerceapp.base.BaseSecondaryFragment
import com.android.ecommerceapp.databinding.FragmentExploreBinding
import com.android.ecommerceapp.model.ExploreProduct
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.util.customSetVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment :
    BaseSecondaryFragment<FragmentExploreBinding, ExploreViewModel, MainViewModel, MainActivity>(
        FragmentExploreBinding::inflate
    ) {
    override val viewModel by viewModels<ExploreViewModel>()
    override val viewModel2 by activityViewModels<MainViewModel>()
    private lateinit var adapter: CategoryAdapter
    private var customerOrderItem: ArrayList<ExploreProduct>? = null

    override fun onCreateFinished() {

        binding.scroolView.customSetVisibility(false)
        binding.shimmerLayout.customSetVisibility(true)
        binding.shimmerLayout.startShimmer()
        binding.recyclerview.addItemDecoration(ItemDecoration())
        viewModel.getCategoryItems()
    }
    override fun initializeListeners() {
        customerOrderItem = viewModel.fetchCount()
        viewModel2.calculateOrderPrice()
        activity().activityBinding.basketPriceText.text = viewModel2.getTotalAccount()
    }

    override fun observeEvents() {
        observeProductLiveData()
    }

    private fun observeProductLiveData() {

        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    activity().hideProgress()
                    binding.shimmerLayout.stopShimmer()
                    binding.scroolView.customSetVisibility(true)
                    binding.shimmerLayout.customSetVisibility(false)
                    it.data?.let { response ->
                        setupAdapter(response, customerOrderItem)
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
    private fun setupAdapter(
        categoryList: List<ExploreProduct>,
        fetchItemCount: ArrayList<ExploreProduct>? = null
    ) {
        adapter = CategoryAdapter(categoryList, fetchItemCount)
        binding.apply {
            recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerview.adapter = adapter

            adapter.listenerItemClick = { newItem ->
                viewModel.customerUpdateItemCount(newItem)
                activity().activityBinding.basketPriceText.text = viewModel2.calculateOrderPrice()

            }
        }
    }
}


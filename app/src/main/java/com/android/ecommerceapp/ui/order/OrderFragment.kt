package com.android.ecommerceapp.ui.order

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.ecommerceapp.activity.MainActivity
import com.android.ecommerceapp.activity.MainViewModel
import com.android.ecommerceapp.base.BaseFragment
import com.android.ecommerceapp.base.BaseSecondaryFragment
import com.android.ecommerceapp.databinding.FragmentOrderBinding
import com.android.ecommerceapp.model.ExploreProduct
import com.android.ecommerceapp.model.Result
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OrderFragment :
    BaseSecondaryFragment<FragmentOrderBinding, OrderViewModel,MainViewModel, MainActivity>(FragmentOrderBinding::inflate) {

    override val viewModel: OrderViewModel by viewModels<OrderViewModel>()
    override val viewModel2: MainViewModel by viewModels<MainViewModel>()
    private lateinit var adapter: OrderAdapter
    override fun onCreateFinished() {
        viewModel2.getCustomerOrderList()
    }
    override fun initializeListeners() {

    }

    override fun observeEvents() {
        observeOrder()
    }

    private fun observeOrder(){
        viewModel2.orderLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    activity().hideProgress()
                    it.data?.let { response ->
                        setupAdapter(response)
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

    private fun setupAdapter(orderList: List<ExploreProduct>){
        val filteredList = orderList.filter { exploreProduct ->
            exploreProduct.count != 0
        }.toSet()

        adapter = OrderAdapter(filteredList.toList())
        binding.apply {
            orderRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            orderRecyclerview.adapter = adapter
        }
    }
}
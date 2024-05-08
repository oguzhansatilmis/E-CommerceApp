package com.android.ecommerceapp.ui.order

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.ecommerceapp.activity.MainActivity
import com.android.ecommerceapp.activity.MainViewModel
import com.android.ecommerceapp.base.BaseSecondaryFragment
import com.android.ecommerceapp.databinding.FragmentOrderBinding
import com.android.ecommerceapp.model.ExploreProduct
import com.android.ecommerceapp.sp.SharedPreferencesKey
import com.android.ecommerceapp.sp.SharedPreferencesServices
import com.android.ecommerceapp.ui.explore.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class OrderFragment :
    BaseSecondaryFragment<FragmentOrderBinding, OrderViewModel, MainViewModel, MainActivity>(
        FragmentOrderBinding::inflate
    ) {

    override val viewModel: OrderViewModel by viewModels<OrderViewModel>()
    override val viewModel2: MainViewModel by viewModels<MainViewModel>()

    private lateinit var adapter: OrderAdapter

    @Inject
    lateinit var sharedPreferencesServices: SharedPreferencesServices
     private var nonZeroCountItems :MutableList <ExploreProduct>? = null
    override fun onCreateFinished() {

        activity().activityBinding.toolbarTv.text = "Sipariş"
        binding.orderRecyclerview.addItemDecoration(ItemDecoration())
    }

    override fun initializeListeners() {
      sharedPreferencesServices.fetch<ArrayList<ExploreProduct>>(SharedPreferencesKey.PRODUCT)?.let { it ->

          nonZeroCountItems = it.filter { it.count != 0 }.toMutableList()
            nonZeroCountItems?.let {
                setupAdapter(it)
            }
        }
    }

    override fun observeEvents() {}

    private fun setupAdapter(orderList: List<ExploreProduct>) {
        adapter = OrderAdapter(orderList)
        binding.apply {
            orderRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            orderRecyclerview.adapter = adapter

            adapter.listenerItemClick = { newItem ->
                val existingItem = nonZeroCountItems?.find { it.id == newItem.id }
                if (existingItem != null) {
                    existingItem.count = newItem.count
                } else {
                    nonZeroCountItems?.add(newItem)
                }
                nonZeroCountItems?.let { products ->

                    sharedPreferencesServices.save(products.toMutableList(), SharedPreferencesKey.PRODUCT)

                    sharedPreferencesServices.fetch<ArrayList<ExploreProduct>>(SharedPreferencesKey.PRODUCT)?.let {
                        adapter.updateList(it)
                    }

                }
                viewModel2.calculateOrderPrice()
                activity().activityBinding.basketPriceText.text = viewModel2.getTotalAccount()
            }
        }
    }
}
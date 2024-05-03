package com.android.ecommerceapp.ui.explore

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.ecommerceapp.R
import com.android.ecommerceapp.activity.Items
import com.android.ecommerceapp.activity.MainActivity
import com.android.ecommerceapp.activity.MainViewModel
import com.android.ecommerceapp.base.BaseSecondaryFragment
import com.android.ecommerceapp.databinding.FragmentExploreBinding
import com.android.ecommerceapp.model.ExploreProduct
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.ui.order.OrderFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment :
    BaseSecondaryFragment<FragmentExploreBinding, ExploreViewModel, MainViewModel, MainActivity>(
        FragmentExploreBinding::inflate
    ) {
    override val viewModel by viewModels<ExploreViewModel>()
    override val viewModel2 by activityViewModels<MainViewModel>()
    private lateinit var adapter: CategoryAdapter
//    private lateinit var electronicsAdapter: CategoryAdapter

    override fun onCreateFinished() {



        binding.recyclerview.addItemDecoration(ItemDecoration())
    }

    override fun initializeListeners() {
        activity().getBinding().basketPriceIcon.setOnClickListener {

            val orderFragment = OrderFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, orderFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    override fun observeEvents() {


        if(viewModel2.boolean){
            viewModel2.getCategoryItems()
            observeProductLiveData()
        }
        else{
            setupAdapter(Items.exploreProductList)
        }


    }

    private fun observeProductLiveData(){

        viewModel2.productsLiveData.observe(viewLifecycleOwner) {
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
    private fun setupAdapter(categoryList: List<ExploreProduct>) {
        adapter = CategoryAdapter(categoryList)
//        electronicsAdapter = CategoryAdapter(categoryList)
        binding.apply {
            recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerview.adapter = adapter

//            electronicRecyclerview.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            electronicRecyclerview.adapter = electronicsAdapter
        }
        adapter.listenerItemClick = { count,id ->

     viewModel2.updateItemCount(count,id) }
    }
}


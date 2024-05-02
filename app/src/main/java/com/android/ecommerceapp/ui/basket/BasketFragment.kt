package com.android.ecommerceapp.ui.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.ecommerceapp.MainActivity
import com.android.ecommerceapp.R
import com.android.ecommerceapp.base.BaseFragment
import com.android.ecommerceapp.databinding.FragmentBasketBinding
import com.android.ecommerceapp.ui.explore.ExploreFragment
import com.android.ecommerceapp.ui.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment :
    BaseFragment<FragmentBasketBinding, ExploreViewModel, MainActivity>(FragmentBasketBinding::inflate) {

    override val viewModel: ExploreViewModel by viewModels(
        ownerProducer = {

            this


        }

    )

    override fun onCreateFinished() {


        viewModel.getCategoryItems()
        viewModel.data.observe(viewLifecycleOwner) {

           binding.item.text = it.title
        }

    }

    override fun initializeListeners() {
        println("")
    }

    override fun observeEvents() {
        println("")
    }

}
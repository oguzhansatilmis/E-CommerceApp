package com.android.ecommerceapp.ui.basket

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.android.ecommerceapp.activity.MainActivity
import com.android.ecommerceapp.activity.MainViewModel
import com.android.ecommerceapp.base.BaseFragment
import com.android.ecommerceapp.base.BaseSecondaryFragment
import com.android.ecommerceapp.databinding.FragmentBasketBinding
import com.android.ecommerceapp.ui.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment :
    BaseSecondaryFragment<FragmentBasketBinding, BasketViewModel, MainViewModel, MainActivity>(
        FragmentBasketBinding::inflate
    ) {

    override val viewModel: BasketViewModel by viewModels()
    override val viewModel2: MainViewModel by activityViewModels()

    override fun onCreateFinished() {

    }
    override fun initializeListeners() {
        println("")
    }
    override fun observeEvents() {
        println("")
    }

}
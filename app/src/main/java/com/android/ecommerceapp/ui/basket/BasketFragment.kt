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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding,BasketViewModel,MainActivity>(FragmentBasketBinding::inflate) {

    override val viewModel: BasketViewModel by viewModels<BasketViewModel>()
    override fun onCreateFinished() {
        println("")
    }

    override fun initializeListeners() {
        println("")
    }

    override fun observeEvents() {
        println("")
    }

}
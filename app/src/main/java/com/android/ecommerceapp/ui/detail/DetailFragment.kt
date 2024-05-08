package com.android.ecommerceapp.ui.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.android.ecommerceapp.R
import com.android.ecommerceapp.activity.MainActivity
import com.android.ecommerceapp.activity.MainViewModel
import com.android.ecommerceapp.base.BaseSecondaryFragment
import com.android.ecommerceapp.databinding.FragmentDetailBinding
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.model.enums.MessageType
import com.android.ecommerceapp.util.clickWithDebounce
import com.android.ecommerceapp.util.loadUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment :
    BaseSecondaryFragment<FragmentDetailBinding, DetailViewModel, MainViewModel, MainActivity>(FragmentDetailBinding::inflate) {
    override val viewModel: DetailViewModel by viewModels()
    override val viewModel2: MainViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private var isSelect = false
    private var product: Product? = null
    override fun onCreateFinished() {

        val job = viewModel.viewModelScope.launch {
            getItemDetail()
            viewModel.getFavoritesItem().collect{ list->
                args.itemId?.let {itemId->
                    val isMatched = list.any { it.id.toString() == itemId }
                    isSelect = isMatched
                }
            }
        }
        job.invokeOnCompletion {
            if (isSelect) {
                binding.selectFavorites.setBackgroundResource(R.drawable.select)
            } else {
                binding.selectFavorites.setBackgroundResource(R.drawable.unselect)
            }
        }
    }
    override fun initializeListeners() {}

    override fun observeEvents() {

        binding.selectFavorites.clickWithDebounce {

            if (isSelect) {
                isSelect = false
                binding.selectFavorites.setBackgroundResource(R.drawable.unselect)
                deleteFavorite()
            } else {
                isSelect = true
                binding.selectFavorites.setBackgroundResource(R.drawable.select)
                addFavorite()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getItemDetail() {

        args.itemId?.let { viewModel.getItemDetail(it) }
        
        viewModel.itemLiveData.observe(this) {
            when (it) {
                is Result.Success -> {
                    activity().hideProgress()
                    it.data.let { response ->
                        response?.let { item ->
                            product = item.body()

                            product?.let {
                                binding.apply {
                                    detailImageview.loadUrl(it.image)
                                    detailItemPrice.text = "${it.price} $"
                                    detailItemTitle.text = it.title
                                }
                            }
                        }
                    }
                }
                is Result.Loading -> {
                    activity().showProgress()
                }
                is Result.Error -> {
                    activity().hideProgress()
                    activity().showMessage(it.message, MessageType.ERROR)
                }
            }
        }
    }
    private fun addFavorite() {

        product?.let {

            viewModel.favoritesItemAdd(it) }
    }

    private fun deleteFavorite() {

        product?.let {
            viewModel.favoritesItemDelete(it)
        }
    }
}

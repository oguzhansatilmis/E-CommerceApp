package com.android.ecommerceapp.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.ecommerceapp.MainActivity
import com.android.ecommerceapp.R
import com.android.ecommerceapp.base.BaseFragment
import com.android.ecommerceapp.base.BaseSecondaryFragment
import com.android.ecommerceapp.databinding.FragmentDetailBinding
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.model.enums.MessageType
import com.android.ecommerceapp.ui.explore.ExploreFragment
import com.android.ecommerceapp.ui.explore.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment() :
    BaseSecondaryFragment<FragmentDetailBinding, DetailViewModel,ExploreViewModel, MainActivity>(FragmentDetailBinding::inflate) {
        private lateinit var instance:ExploreFragment
    override val viewModel: DetailViewModel by viewModels<DetailViewModel>()
    override val viewModel2: ExploreViewModel by viewModels<ExploreViewModel>(
        ownerProducer = {
            instance

        }
    )
    private val args: DetailFragmentArgs by navArgs()
    private var product: Product? = null
    override fun onCreateFinished() {
        getItemDetail()

        instance = ExploreFragment.instance
        println(ExploreFragment.instance.deneme)


    }

    override fun initializeListeners() {
        addFavorite()
        deleteFavorite()
        viewModel2.data.observe(this){
            println("gelen veri $it")
        }
    }

    override fun observeEvents() {

    }

    private fun getItemDetail() {

        args.itemId?.let {
            viewModel.getItemDetail(it)
        }
        viewModel.itemLiveData.observe(this) {
            when (it) {
                is Result.Success -> {
                    activity().hideProgress()
                    it.data.let { response ->
                        response?.let { item ->
                            binding.itemId.text = item.body().toString()
                            product = item.body()
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
        binding.addFavorite.setOnClickListener {
            product?.let {
                viewModel.insertFavoriteItem(it)
            }
            Toast.makeText(requireContext(), "Kaydedildi", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteFavorite() {
        binding.deleteFavorite.setOnClickListener {
            product?.let {
                viewModel.deleteFavoriteItem(it)
                Toast.makeText(requireContext(), "Silindi", Toast.LENGTH_LONG).show()
            }

        }
    }
}

package com.android.ecommerceapp.ui.favorites

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Handler
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.ecommerceapp.activity.MainActivity
import com.android.ecommerceapp.base.BaseFragment
import com.android.ecommerceapp.base.BaseSecondaryFragment
import com.android.ecommerceapp.databinding.FragmentFavoritesBinding
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.ui.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class FavoritesFragment : BaseSecondaryFragment<FragmentFavoritesBinding, FavoritesViewModel,DetailViewModel, MainActivity>(
    FragmentFavoritesBinding::inflate
) {
    private lateinit var adapter: FavoritesAdapter
    override val viewModel: FavoritesViewModel by viewModels()
    override val viewModel2: DetailViewModel by viewModels()

    override fun onCreateFinished() {

        activity().activityBinding.toolbarTv.text = "Favorilerim"
        viewModel.getUserFavorites()
    }

    override fun initializeListeners() {}

    override fun observeEvents() {

        viewModel.getUserFavoritesLiveData.observe(this) {
            when (it) {
                is Result.Success -> {
                    it.data?.let { data ->
                        setupAdapter(data)
                        println("gelen data $data")
                    }
                }
                is Result.Loading -> {
                    println("Loading")
                }
                is Result.Error -> {}
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupAdapter(favorites: MutableList<Product>) {
        adapter = FavoritesAdapter(favorites)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallBack(adapter, requireActivity()))
        itemTouchHelper.attachToRecyclerView(binding.favoritesRecyclerview)

        binding.apply {
            favoritesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            favoritesRecyclerview.adapter = adapter
        }


        adapter.onSwipeDeleteItem = {
            viewModel2.favoritesItemDelete(it)
            println("item silindi")
        }
    }
}
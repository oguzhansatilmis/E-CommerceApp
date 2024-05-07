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
import com.android.ecommerceapp.databinding.FragmentFavoritesBinding
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel, MainActivity>(
    FragmentFavoritesBinding::inflate
) {
    private lateinit var adapter: FavoritesAdapter
    override val viewModel: FavoritesViewModel by viewModels(
        ownerProducer = { this }
    )

    override fun onCreateFinished() {
        viewModel.getUserFavorites()
    }

    override fun initializeListeners() {}

    override fun observeEvents() {

        viewModel.getUserFavoritesLiveData.observe(this) {
            when (it) {
                is Result.Success -> {
                    it.data?.let { data ->

                        setupAdapter(data)
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
    private fun setupAdapter(favorites: MutableList<FavoritesEntity>) {
        adapter = FavoritesAdapter(favorites)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallBack(adapter, requireActivity()))
        itemTouchHelper.attachToRecyclerView(binding.favoritesRecyclerview)

        binding.apply {
            favoritesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            favoritesRecyclerview.adapter = adapter
        }


        adapter.onSwipeDeleteItem = {
            viewModel.viewModelScope.launch {
                activity().showProgress()
                withContext(Dispatchers.IO) {
                    delay(5000)
                    viewModel.deleteItemForId(it.id)
                }
                activity().hideProgress()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("çalıştı")
    }
}
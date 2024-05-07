package com.android.ecommerceapp.ui.favorites

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.ecommerceapp.activity.MainActivity
import com.android.ecommerceapp.base.BaseFragment
import com.android.ecommerceapp.databinding.FragmentFavoritesBinding
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.model.Result
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel, MainActivity>(
    FragmentFavoritesBinding::inflate
) {
    private lateinit var adapter: FavoritesAdapter
    override val viewModel: FavoritesViewModel by viewModels<FavoritesViewModel>()

    override fun onCreateFinished() {}

    override fun initializeListeners() {}

    override fun observeEvents() {
     viewModel.getUserFavorites()
        viewModel.getUserFavoritesLiveData.observe(this) {
            when (it) {
                is Result.Success -> {
                    println("data1 ${it.data}")
                    it.data?.let { data ->
                        println("data1 $data")
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
    private fun setupAdapter(favorites: List<FavoritesEntity>) {
        adapter = FavoritesAdapter(favorites)
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallBack(adapter, requireActivity()))
        itemTouchHelper.attachToRecyclerView(binding.favoritesRecyclerview)

        binding.apply {
            favoritesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            favoritesRecyclerview.adapter = adapter
        }
        adapter.onSwipeDeleteItem = {
            println("item silindi $it")
            viewModel.deleteItemFavorites(it)
//           adapter.updateList(favorites)

        }


    }

}
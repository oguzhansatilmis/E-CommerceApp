package com.android.ecommerceapp.ui.favorites

import androidx.fragment.app.viewModels
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

    override fun onCreateFinished() {
        getFavorites()
    }

    override fun initializeListeners() {
        println("")
    }

    override fun observeEvents() {
        println("")
    }

    private fun getFavorites() {
        viewModel.getUserFavorites()
        viewModel.getUserFavoritesLiveData.observe(this) {

            when (it) {
                is Result.Success -> {
                    it.data?.let { data ->
                        setupAdapter(data)
                    }
                }
                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }
    }

    private fun setupAdapter(favorites: List<FavoritesEntity>) {
        adapter = FavoritesAdapter(favorites)
        binding.apply {
            favoritesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            favoritesRecyclerview.adapter = adapter
        }
    }

}
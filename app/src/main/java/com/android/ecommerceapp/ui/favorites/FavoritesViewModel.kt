package com.android.ecommerceapp.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.repository.CommerceRepository
import com.android.ecommerceapp.sp.SharedPreferencesKey
import com.android.ecommerceapp.sp.SharedPreferencesServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: CommerceRepository) :
    ViewModel() {

    private val _getUserFavoritesLiveData =
        MutableLiveData<Result<MutableList<Product>>>(Result.Loading())
    val getUserFavoritesLiveData = _getUserFavoritesLiveData

    @Inject
        lateinit var sharedPreferencesServices: SharedPreferencesServices

    fun getUserFavorites() {
        viewModelScope.launch {
            val result = sharedPreferencesServices.fetch<MutableList<Product>>(SharedPreferencesKey.FAVORITES)
            result?.let {
                _getUserFavoritesLiveData.value = Result.Success(it.toMutableList())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("onCleared() run ")
    }
}
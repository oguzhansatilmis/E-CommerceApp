package com.android.ecommerceapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.repository.CommerceRepository
import com.android.ecommerceapp.sp.SharedPreferencesKey
import com.android.ecommerceapp.sp.SharedPreferencesServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CommerceRepository
) : ViewModel() {

    @Inject
    lateinit var sharedPreferencesServices: SharedPreferencesServices
    private val _itemDetailLiveData = MutableLiveData<Result<Response<Product>>>(Result.Loading())
    val itemLiveData: MutableLiveData<Result<Response<Product>>> = _itemDetailLiveData

    fun getItemDetail(itemId: String) {

        viewModelScope.launch {
            val itemDetailResult = repository.getItemDetail(itemId)

            itemDetailResult?.let { response ->

                if (response.isSuccessful) {
                    _itemDetailLiveData.value = Result.Success(response)
                } else {
                    _itemDetailLiveData.value = Result.Error("Hata")
                }
            }
        }
    }
    fun getFavoritesItem(): Flow<MutableList<Product>> {
        return flow {
            val favorites =
                sharedPreferencesServices.fetch<MutableList<Product>>(SharedPreferencesKey.FAVORITES)
            favorites?.let {
                emit(favorites)
            }
        }
    }
    fun favoritesItemAdd(favoriteProduct: Product) {
        // Önce mevcut favori listesini alalım
        val currentFavorites =
            sharedPreferencesServices.fetch<MutableList<Product>>(SharedPreferencesKey.FAVORITES) // Örneğin, favorileri getiren bir metod kullanılmalı

        // Eğer mevcut favori listesi yoksa, yeni bir liste oluşturarak favori ürünü ekleyin
        val newFavorites = currentFavorites?.toMutableList()?.apply { add(favoriteProduct) }
            ?: mutableListOf(favoriteProduct)

        // SharedPreferences üzerinde yeni favori listesini kaydedin
        sharedPreferencesServices.save(newFavorites, SharedPreferencesKey.FAVORITES)
    }

    fun favoritesItemDelete(favoriteProduct: Product) {
        // Önce mevcut favori listesini alalım
        val currentFavorites =
            sharedPreferencesServices.fetch<MutableList<Product>>(SharedPreferencesKey.FAVORITES) // Örneğin, favorileri getiren bir metod kullanılmalı

        // Eğer mevcut favori listesi varsa, bu ürünü listeden silin
        currentFavorites?.remove(favoriteProduct)

        // SharedPreferences üzerinde güncellenmiş favori listesini kaydedin
        if (currentFavorites != null) {
            sharedPreferencesServices.save(currentFavorites, SharedPreferencesKey.FAVORITES)
        }
    }


}


package com.android.ecommerceapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Rating
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.repository.CommerceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: CommerceRepository
) : ViewModel() {

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
    fun insertFavoriteItem(product: Product) {

        viewModelScope.launch {
            val favoritesEntity = FavoritesEntity(
                id = product.id,
                title = product.title,
                price = product.price,
                description = product.description,
                category = product.category,
                image = product.image,
                rating = Rating(rate = product.rating.rate, count = product.rating.count)
            )
            repository.insetFavoriteItem(favoritesEntity)
        }
    }
    fun deleteFavoriteItem(product: Product){
        viewModelScope.launch {
            val favoritesEntity = FavoritesEntity(
                id = product.id,
                title = product.title,
                price = product.price,
                description = product.description,
                category = product.category,
                image = product.image,
                rating = Rating(rate = product.rating.rate, count = product.rating.count)
            )
            repository.deleteItemFavorites(favoritesEntity)
        }
    }

}
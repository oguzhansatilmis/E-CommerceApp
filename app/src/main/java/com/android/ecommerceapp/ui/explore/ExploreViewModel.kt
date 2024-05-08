package com.android.ecommerceapp.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ecommerceapp.model.ExploreProduct
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.repository.CommerceRepository
import com.android.ecommerceapp.sp.SharedPreferencesKey
import com.android.ecommerceapp.sp.SharedPreferencesServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: CommerceRepository
) : ViewModel() {
    private val _productsLiveData = MutableLiveData<Result<List<ExploreProduct>>>(Result.Loading())
    val productsLiveData: LiveData<Result<List<ExploreProduct>>> = _productsLiveData

    private var productList = arrayListOf<ExploreProduct>()

    private var fetchCount: ArrayList<ExploreProduct>? = null

    @Inject
    lateinit var sharedPreferencesServices: SharedPreferencesServices

    fun getCategoryItems() {
        viewModelScope.launch {
            val productItems = repository.getAllProduct()

            productItems?.let { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let { body ->

                        val exploreProductList = body.map { item ->
                            ExploreProduct(
                                id = item.id,
                                title = item.title,
                                price = item.price,
                                image = item.image,
                            )
                        }
                        _productsLiveData.value = Result.Success(exploreProductList)
                    }
                }
                _productsLiveData.value = Result.Error("exploreProduct Error")
            }
        }

    }

    fun fetchCount(): ArrayList<ExploreProduct>? {
        fetchCount = sharedPreferencesServices.fetch<ArrayList<ExploreProduct>>(SharedPreferencesKey.PRODUCT)
        return fetchCount
    }

    fun customerUpdateItemCount(newItem: ExploreProduct) {

        val existingItem = productList.find { it.id == newItem.id }
        if (existingItem != null) {
            existingItem.count = newItem.count
        } else {
            productList.add(newItem)
        }
        sharedPreferencesServices.save(productList.toMutableList(), SharedPreferencesKey.PRODUCT)
    }

//    override fun onCleared() {
//        println("Explore VM onCleared running")
//    }

}
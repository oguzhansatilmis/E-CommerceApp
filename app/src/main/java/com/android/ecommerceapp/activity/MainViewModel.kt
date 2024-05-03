package com.android.ecommerceapp.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ecommerceapp.model.ExploreProduct
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.repository.CommerceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CommerceRepository
) : ViewModel() {

    private val _productsLiveData = MutableLiveData<Result<List<ExploreProduct>>>(Result.Loading())
    val productsLiveData: LiveData<Result<List<ExploreProduct>>> = _productsLiveData


    private val _orderLiveData = MutableLiveData<Result<List<ExploreProduct>>>(Result.Loading())
    val orderLiveData: LiveData<Result<List<ExploreProduct>>> = _orderLiveData


    private var exploreProductList = listOf<ExploreProduct>()
    private var customerOrderList = mutableListOf<ExploreProduct>()


    var boolean = true



     fun getCategoryItems() {
        viewModelScope.launch {
            val productItems = repository.getAllProduct()

            productItems?.let { response ->
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let { body ->

                        exploreProductList = body.map { item ->
                            ExploreProduct(
                                id = item.id,
                                title = item.title,
                                price = item.price,
                                image = item.image,
                            )
                        }

                        Items.exploreProductList = exploreProductList
                        _productsLiveData.value = Result.Success(exploreProductList)
                    }
                }
                _productsLiveData.value = Result.Error("exploreProduct")
            }
        }

         boolean = false
    }

    fun getCustomerOrderList() {
        viewModelScope.launch {
            Items.exploreProductList.forEach {
                if (it.count > 0) {
                    customerOrderList.add(it)
                }
            }
            customerOrderList.sortedByDescending { it.count }
            _orderLiveData.value = Result.Success(customerOrderList)

        }
    }

    fun updateItemCount(newCount: Int, id: Long) {
        viewModelScope.launch {
            exploreProductList.forEach {
                if (it.id == id) {
                    it.count = newCount
                }
            }
            _productsLiveData.value = Result.Success(exploreProductList)
        }
    }
}






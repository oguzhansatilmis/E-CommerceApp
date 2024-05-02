package com.android.ecommerceapp.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ecommerceapp.model.BaseException
import com.android.ecommerceapp.model.Product
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.model.SelectedItems
import com.android.ecommerceapp.repository.CommerceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: CommerceRepository
) : ViewModel() {

    private var _data = MutableLiveData<SelectedItems>()
    val data: LiveData<SelectedItems> = _data

    private val _productsLiveData =
        MutableLiveData<Result<Response<List<Product>>>>(Result.Loading())
    val productsLiveData: LiveData<Result<Response<List<Product>>>> = _productsLiveData

    fun getCategoryItems() {
        viewModelScope.launch {
            val productItems = repository.getAllProduct()

            productItems?.let { response ->
                if (response.isSuccessful) {
                    _productsLiveData.value = Result.Success(response)
                } else {
                    _productsLiveData.value = Result.Error("Error message", response)
                }
            }
        }
    }

    fun setData(value: SelectedItems) {
        _data.value = value
    }

    override fun onCleared() {
        println("Explore VM onCleared running")
    }

}
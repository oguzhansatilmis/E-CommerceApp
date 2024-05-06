package com.android.ecommerceapp.activity

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
class MainViewModel @Inject constructor(
    private val repository: CommerceRepository
) : ViewModel() {
    @Inject
    lateinit var sharedPreferencesServices: SharedPreferencesServices

    fun calculateOrderPrice(): String {
        var totalPrice = 0.0
        val fetchCount = sharedPreferencesServices.fetch<ArrayList<ExploreProduct>>(SharedPreferencesKey.PRODUCT)

        fetchCount?.let { item ->
            item.forEach {
                println(it)
                val price = it.count * it.price!!
                totalPrice += price
            }
        }

        sharedPreferencesServices.save(totalPrice.toString(), SharedPreferencesKey.TOTAL_ACCOUNT) ?: 0
        return "$totalPrice $"

    }

    fun getTotalAccount(): String {
         sharedPreferencesServices.fetch<String>(SharedPreferencesKey.TOTAL_ACCOUNT)?.let {
             return it
         }
        return "0"
    }


}







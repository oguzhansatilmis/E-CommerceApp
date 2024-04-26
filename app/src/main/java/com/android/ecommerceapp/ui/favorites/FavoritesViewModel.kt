package com.android.ecommerceapp.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.model.Result
import com.android.ecommerceapp.repository.CommerceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
   private val repository: CommerceRepository
):ViewModel() {

    private val _getUserFavoritesLiveData= MutableLiveData<Result<List<FavoritesEntity>>>(Result.Loading())
    val getUserFavoritesLiveData = _getUserFavoritesLiveData

    fun getUserFavorites(){
        viewModelScope.launch {
            val result= repository.getFavoritesList()
            result?.let {
                _getUserFavoritesLiveData.value = Result.Success(result)
            }

        }
    }

}
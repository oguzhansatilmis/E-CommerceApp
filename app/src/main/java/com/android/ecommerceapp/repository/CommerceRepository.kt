package com.android.ecommerceapp.repository

import androidx.annotation.LongDef
import com.android.ecommerceapp.base.BaseRepository
import com.android.ecommerceapp.db.ModuleDao
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.service.ApiService
import javax.inject.Inject

class CommerceRepository @Inject constructor(
    private val apiService: ApiService,
    private val dao: ModuleDao
) : BaseRepository() {


    suspend fun getItemDetail(itemId: String) = safeApiCall {
        apiService.getItemDetail(itemId)
    }

    suspend fun getAllProduct() = safeApiCall {
        apiService.getAllProducts()
    }

    suspend fun getFavoritesList() = safeApiCall {
        dao.getFavoritesList()
    }

    suspend fun insetFavoriteItem(favoritesEntity: FavoritesEntity) = safeApiCall {
        dao.insertModule(favoritesEntity)
    }

    suspend fun deleteItemFavorites(favoritesEntity: FavoritesEntity) = safeApiCall {
        dao.deleteItemFavoritesList(favoritesEntity)
    }

}
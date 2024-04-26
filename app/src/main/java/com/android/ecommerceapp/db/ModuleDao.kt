package com.android.ecommerceapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.ecommerceapp.model.FavoritesEntity
import com.android.ecommerceapp.model.Product

@Dao
interface ModuleDao {

    @Query("SELECT * FROM favorites_table")
    fun getFavoritesList(): List<FavoritesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModule(favoritesEntity: FavoritesEntity)

    @Delete
    fun deleteItemFavoritesList(favoritesEntity:FavoritesEntity)
}
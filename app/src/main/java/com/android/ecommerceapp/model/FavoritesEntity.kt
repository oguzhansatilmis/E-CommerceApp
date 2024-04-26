package com.android.ecommerceapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites_table")
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val price: Double,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val category: String,
    @ColumnInfo
    val image: String,
    @ColumnInfo
    val rating: Rating

)



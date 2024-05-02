package com.android.ecommerceapp.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedItems(
    val id:Long,
    val title: String,
    val price:Double,
    val image: String,
): Parcelable

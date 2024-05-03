package com.android.ecommerceapp.model

data class ExploreProduct(

    val id: Long?=null,
    val title: String?=null,
    val price: Double?=null,
    val image: String?=null,
    var count:Int = 0
)

package com.android.ecommerceapp.model

data class ExploreProduct(

    var id: Long?=null,
    val title: String?=null,
    val price: Double?=null,
    val image: String?=null,
    var count:Int = 0
)

package com.android.ecommerceapp.service

import com.android.ecommerceapp.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/{itemId}")
    suspend fun getItemDetail(@Path("itemId") itemId: String): Response<Product>
}
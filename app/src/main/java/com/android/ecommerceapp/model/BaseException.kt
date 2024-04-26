package com.android.ecommerceapp.model

data class BaseException(val code: Int? = null, val error: String? = null) : Exception()


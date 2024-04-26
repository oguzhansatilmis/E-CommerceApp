package com.android.ecommerceapp.util

import android.content.res.Resources

object Device {
    private val displayMetrics = Resources.getSystem().displayMetrics

    val width get() = displayMetrics.widthPixels
    val height get() = displayMetrics.heightPixels

}
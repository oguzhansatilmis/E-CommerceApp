package com.android.ecommerceapp.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide


fun ImageView.loadUrl(path:String)
{
    Glide.with(this)
        .load(path)
        .into(this)
}
val Float.widthRatio: Int get() = (Device.width * this).toInt()
val Float.heightRatio: Int get() = (Device.height * this).toInt()

fun View.customSetVisibility(visibility:Boolean){

    if (visibility){ this.visibility =View.VISIBLE} else this.visibility =View.GONE
}

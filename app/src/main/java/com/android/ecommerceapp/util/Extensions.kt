package com.android.ecommerceapp.util

import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.text.DecimalFormat

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

fun Double.toFormat():String{
    val formatter = DecimalFormat("#.##")
    formatter.maximumFractionDigits = 2
    val formattedPrice = formatter.format(this)

    return formattedPrice.toDouble().toString()
}

fun View.clickWithDebounce(debounceTime: Long = Constants.debounceTime, action: (View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

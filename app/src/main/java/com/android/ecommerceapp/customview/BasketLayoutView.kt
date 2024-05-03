package com.android.ecommerceapp.customview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.ecommerceapp.R
import com.android.ecommerceapp.util.customSetVisibility

class BasketLayoutView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    private val minusBtn: ImageButton
    private val deleteBtn: ImageButton
    private val addBtn: ImageButton
    private val countTv: TextView
    private val linearLayout: LinearLayout

    private var buttonContainerSize: Int =
        context.resources.getDimensionPixelSize(R.dimen.action_button_size)
    private var textContainerHeight: Int =
        context.resources.getDimensionPixelSize(R.dimen.action_text_height_detail)
    private var textContainerWidth: Int =
        context.resources.getDimensionPixelSize(R.dimen.action_text_width_detail)
    private var countTextSize = context.resources.getDimension(R.dimen.action_text_size_detail)


    private var orientation = 0

    private var count = 0


    init {
        isInEditMode
        loadAttributes(attrs)
        LayoutInflater.from(context).inflate(R.layout.custombasketview, this, true)

        radius = context.resources.getDimension(R.dimen.action_card_corner_radius)
        cardElevation = context.resources.getDimension(R.dimen.action_card_elevation)

        minusBtn = findViewById(R.id.minusBtn)
        deleteBtn = findViewById(R.id.deleteBtn)
        addBtn = findViewById(R.id.addBtn)
        countTv = findViewById(R.id.countTv)

        linearLayout = findViewById(R.id.viewContainer)
        //Init custom view

        //set custom view dp
        applyOrientation()
        setViewSize(minusBtn, buttonContainerSize)
        setViewSize(deleteBtn, buttonContainerSize)
        setViewSize(addBtn, buttonContainerSize)

        setTextContainerSize(textContainerHeight, textContainerWidth)
        countTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, countTextSize)

    }
    fun updateCount(newCount:Int){

        count = newCount

        countTv.text = count.toString()

        if(count>0){
            countTv.customSetVisibility(true)

            if (count ==1){
                deleteBtn.customSetVisibility(true)
                minusBtn.customSetVisibility(false)
            }
            else{
                deleteBtn.customSetVisibility(false)
                minusBtn.customSetVisibility(true)
            }

        }
    }

    fun setOnClickDecrease(action: () -> Unit) {
        minusBtn.setOnClickListener {
            action()
            decraseItem()
        }
    }

    fun setOnClickIncrease(action: () -> Unit) {
        addBtn.setOnClickListener {
            action()
            increaseItem()
        }
    }

    fun setOnClickTrash(action: () -> Unit) {
        deleteBtn.setOnClickListener {
            action
            clearItemCount()
        }

    }

    @Suppress("DEPRECATION")
    private fun loadAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.BasketLayoutView, 0, 0).apply {
            try {
                buttonContainerSize = getDimensionPixelSize(
                    R.styleable.BasketLayoutView_buttonContainerSize,
                    buttonContainerSize
                )
                textContainerHeight = getDimensionPixelSize(
                    R.styleable.BasketLayoutView_textContainerHeight,
                    textContainerHeight
                )
                textContainerWidth = getDimensionPixelSize(
                    R.styleable.BasketLayoutView_textContainerWidth,
                    textContainerWidth
                )
                countTextSize = getDimension(
                    R.styleable.BasketLayoutView_textSize,
                    resources.getDimension(R.dimen.action_text_size_detail)
                ) / resources.displayMetrics.scaledDensity
                orientation = getInt(R.styleable.BasketLayoutView_orientation, orientation)
            } finally {
                recycle()
            }
        }
    }

    private fun applyOrientation() {
        linearLayout.orientation = orientation
//        swapViewsInLinearLayout()
    }

    private fun setViewSize(view: View, size: Int) {

        val params = view.layoutParams.apply {
            width = size
            height = size
        }
        view.layoutParams = params
    }

    private fun setTextContainerSize(textContainerHeight: Int, textContainerWidth: Int) {
        val params = countTv.layoutParams
        params.height = textContainerHeight
        params.width = textContainerWidth
        countTv.layoutParams = params
    }

    private fun decraseItem() {

        if (count > 1) {
            count--
            if (count == 1) {
                deleteBtn.customSetVisibility(true)
                minusBtn.customSetVisibility(false)
            }else{
                deleteBtn.customSetVisibility(false)
                minusBtn.customSetVisibility(true)
            }
        }
        countTv.text = count.toString()
    }

    private fun increaseItem() {
        count++
        deleteBtn.customSetVisibility(false)
        minusBtn.customSetVisibility(true)
        countTv.text = count.toString()

    }

    private fun clearItemCount() {
       this.customSetVisibility(false)
    }

}
package com.android.ecommerceapp.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.ecommerceapp.R
import com.android.ecommerceapp.util.customSetVisibility

class VerticalBasketLayout @JvmOverloads constructor(

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
        context.resources.getDimensionPixelSize(R.dimen.vertical_basket_size)
    private var textContainerHeight: Int =
        context.resources.getDimensionPixelSize(R.dimen.vertical_basket_size)
    private var textContainerWidth: Int =
        context.resources.getDimensionPixelSize(R.dimen.vertical_basket_size)
    private var countTextSize = context.resources.getDimension(R.dimen.action_text_size_detail)


    private var orientation = 1

    private var count = 0

    init {

        isInEditMode
        loadAttributes(attrs)
        LayoutInflater.from(context).inflate(R.layout.vertical_basket_layout, this, true)

        radius = context.resources.getDimension(R.dimen.action_card_corner_radius)
        cardElevation = context.resources.getDimension(R.dimen.action_card_elevation)

        minusBtn = findViewById(R.id.vMinusBtn)
        deleteBtn = findViewById(R.id.vDeleteBtn)
        addBtn = findViewById(R.id.vAddBtn)
        countTv = findViewById(R.id.vCountTv)

        linearLayout = findViewById(R.id.vViewContainer)
        //Init custom view

        //set custom view dp
        applyOrientation()
        setViewSize(minusBtn, buttonContainerSize)
        setViewSize(deleteBtn, buttonContainerSize)
        setViewSize(addBtn, buttonContainerSize)

        setTextContainerSize(textContainerHeight, textContainerWidth)
        countTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, countTextSize)

        countTv.customSetVisibility(false)
        deleteBtn.customSetVisibility(false)
        minusBtn.customSetVisibility(false)
        countTv.text = count.toString()
    }


    fun setOnClickDecrease(action: (Int) -> Unit) {
        minusBtn.setOnClickListener {
            decraseItem()
            action(count)

        }
    }

    fun setOnClickIncrease(action: (Int) -> Unit) {
        addBtn.setOnClickListener {
            increaseItem()
            action(count)

        }
    }

    fun setOnClickTrash(action: (Int) -> Unit) {
        deleteBtn.setOnClickListener {
            clearItemCount()
            action(count)

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

    @SuppressLint("WrongConstant")
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
            } else {
                deleteBtn.customSetVisibility(false)
                minusBtn.customSetVisibility(true)
            }
        }
        countTv.text = count.toString()
    }

    private fun increaseItem() {
        count++
        countTv.customSetVisibility(true)
        if (count == 1) {
            deleteBtn.customSetVisibility(true)
            minusBtn.customSetVisibility(false)
        } else {
            deleteBtn.customSetVisibility(false)
            minusBtn.customSetVisibility(true)
        }
        countTv.text = count.toString()

    }

    private fun clearItemCount() {
        deleteBtn.customSetVisibility(false)
        minusBtn.customSetVisibility(false)
        count = 0
        countTv.customSetVisibility(false)

    }

}
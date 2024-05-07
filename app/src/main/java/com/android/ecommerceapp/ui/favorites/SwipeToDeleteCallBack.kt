package com.android.ecommerceapp.ui.favorites

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.ecommerceapp.R
import javax.inject.Inject

class SwipeToDeleteCallBack @Inject constructor(
    private val adapter: RecyclerView.Adapter<*>,
    private val context: Context
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            adapter as FavoritesAdapter
            adapter.removeItem(viewHolder.layoutPosition)
            println("position ${viewHolder.layoutPosition}")
        }

    }
/*
  override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val background = ColorDrawable(Color.RED)
        val deleteIcon = ContextCompat.getDrawable(context, R.drawable.trash)
        val iconMargin = (itemView.height - deleteIcon?.intrinsicHeight!!) / 2

        // Swipe yönüne bağlı olarak arka planı çizdir
        if (dX > 0) { // Sağa swipe
            background.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
        } else if (dX < 0) { // Sola swipe
            background.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
        }
        background.draw(c)

        // Silme ikonunu çizdir
        val iconTop = itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
        val iconBottom = iconTop + deleteIcon.intrinsicHeight
        val iconLeft: Int
        val iconRight: Int
        if (dX > 0) { // Sağa swipe
            iconLeft = itemView.left + iconMargin
            iconRight = itemView.left + iconMargin + deleteIcon.intrinsicWidth
        } else { // Sola swipe
            iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
            iconRight = itemView.right - iconMargin
        }
        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
 */


}
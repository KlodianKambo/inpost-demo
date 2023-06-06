package pl.inpost.recruitmenttask.ui.shipmentList.adapter

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ShipmentItemDecorator(private val space: Int, private val backgroundColor: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        // Set the desired spacing for each item
        outRect.top = space
        outRect.bottom = space

        // Add additional logic if needed, e.g., for the first and last items
        if (position == 0) {
            outRect.top = 0 // Remove top spacing for the first item
        }

        val itemCount = parent.adapter?.itemCount ?: 0
        if (position == itemCount - 1) {
            outRect.bottom = 0 // Remove bottom spacing for the last item
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)

            val top = view.bottom.toFloat()
            val bottom = top + space

            val backgroundRect = Rect(left, top.toInt(), right, bottom.toInt())
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = backgroundColor
            }

            canvas.drawRect(backgroundRect, paint)
        }
    }
}
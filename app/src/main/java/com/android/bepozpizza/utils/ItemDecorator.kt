package com.android.bepozpizza.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.bepozpizza.presentation.home.adapter.PizzaAdapter

class ItemDecorator(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let { adapter ->
            val itemCount = adapter.itemCount
            val position = parent.getChildAdapterPosition(view)
            if (adapter is PizzaAdapter) {
                if (position != itemCount.minus(1)) {
                    outRect.bottom = spacing
                }
            }
        }
    }
}
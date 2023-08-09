package com.hash.template.widgets

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceDecoration(
    private val left: Int = 0,
    private val top: Int = 0,
    private val right: Int = 0,
    private val bottom: Int = 0
) : RecyclerView.ItemDecoration() {

    constructor(horizontal: Int, vertical: Int) : this(horizontal, vertical, horizontal, vertical)

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.set(left, top, right, bottom)
    }
}

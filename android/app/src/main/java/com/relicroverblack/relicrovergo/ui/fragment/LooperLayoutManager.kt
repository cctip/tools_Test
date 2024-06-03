package com.relicroverblack.relicrovergo.ui.fragment

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView


class LooperLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}

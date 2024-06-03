package com.relicroverblack.relicrovergo.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.ui.fragment.ItemModel

class LoopingRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val scrollHandler = android.os.Handler()
    private val scrollRunnable = object : Runnable {
        override fun run() {
            scrollBy(0, 5) // 每次滚动的像素数
            scrollHandler.postDelayed(this, 30) // 滚动间隔时间
        }
    }

    fun startAutoScroll() {
        scrollHandler.post(scrollRunnable)
    }

    fun stopAutoScroll() {
        scrollHandler.removeCallbacks(scrollRunnable)
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        val layoutManager = layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        // 如果滚动到列表末尾，则将列表滚动到起始位置，实现循环
        if (lastVisibleItemPosition == itemCount - 1) {
            layoutManager.scrollToPositionWithOffset(1, 0)
        } else if (firstVisibleItemPosition == 0) {
            layoutManager.scrollToPositionWithOffset(itemCount - 2, 0)
        }
    }
}

class LoopingAdapter(private val items: MutableList<ItemModel>) : RecyclerView.Adapter<LoopingAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemimg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoppingturn, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPosition = position % items.size
        val item = items[itemPosition]
        holder.imageView.setImageResource(item.imageResId)
    }
    fun updateItem(newItem: ItemModel) {
        val index = items.indexOfFirst { it.imageResId == newItem.imageResId }
        if (index != -1) {
            items[index] = newItem
            notifyDataSetChanged()
        } else {
            items.add(newItem)
            notifyDataSetChanged()
        }
    }
    override fun getItemCount(): Int {
        return Int.MAX_VALUE // 使得item数无限大，形成循环
    }
}
package com.hash.tooltemplate.ui.adapter

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import java.util.Random

class GridAdapter(private val items: List<GridItem>) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    private var lastAnimatedPosition = -1
    private var expandedPositions = mutableListOf<Int>()
    private var expanded = mutableListOf<Int>()
    private var currentExpandedPosition: Int? = null // 记录当前应该被放大的图片位置

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memorgrid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
    }

    fun expandItemAtPosition(position: Int) {
        expandedPositions.add(position)
        expanded.add(position)
        notifyItemChanged(position)
    }

    fun isExpanded(position: Int): Boolean {
        return expandedPositions.contains(position)
    }

    fun resetExpandedPositions() {
        expandedPositions.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.item_memorimg)

        fun bind(item: GridItem, position: Int) {
            imageView.apply {
                // 假设GridItem有一个getImageResourceId()方法或者属性
                setImageResource(item.imageResourceId) // 直接使用item中的资源ID设置图片

                // 更新布局参数来改变图片大小
                layoutParams = layoutParams.apply {
                    width = if (isExpanded(position)) 280 else 200
                    height = if (isExpanded(position)) 280 else 200
                }
                requestLayout()
            }
            itemView.setOnClickListener {
                Log.i("expandedPositions",expanded.toString())
                Log.i("position",position.toString())
                if ( expanded.contains(position)) {
                    imageView.apply {
                        setImageResource(item.imageResourceId)
                        layoutParams = layoutParams.apply {
                            width = 280
                            height = 280
                        }
                        requestLayout()
                    }
                }else{
                    //
                }
            }
        }
    }

}
data class GridItem(val imageResourceId: Int, val backgroundImageResourceId: Int)

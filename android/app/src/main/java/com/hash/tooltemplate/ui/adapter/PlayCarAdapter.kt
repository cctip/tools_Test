package com.hash.tooltemplate.ui.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R

class PlayCarAdapter(private val context: Context, private val imageList: Array<Int>) :
    RecyclerView.Adapter<PlayCarAdapter.ImageViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayCarAdapter.ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_playcar, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val adjustedPosition = position % imageList.size
        val handler = Handler(Looper.getMainLooper())

        // 使用 Handler 或 postDelayed 来延迟设置图片，实现停留效果
        handler.postDelayed({
            holder.imageView.setImageResource(imageList[adjustedPosition])
        }, 500) // 0.5秒的延迟
    }
    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }
    //获取指定索引位置的图片资源id
    fun getImageResourceId(index: Int): Int {
        val adjustedIndex = index % imageList.size
        return imageList[adjustedIndex]
    }
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageVieww)
    }
}
package com.relicroverblack.relicrovergo.ui.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.relicroverblack.relicrovergo.R

class PlayCarAdapter(private val context: Context, private val imageList: Array<Int>) :
    RecyclerView.Adapter<PlayCarAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayCarAdapter.ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_playcar, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val adjustedPosition = position % imageList.size
        holder.imageView.setImageResource(imageList[adjustedPosition])
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    fun getImageResourceId(index: Int): Int {
        val adjustedIndex = index % imageList.size
        return imageList[adjustedIndex]
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageVieww)
    }
}

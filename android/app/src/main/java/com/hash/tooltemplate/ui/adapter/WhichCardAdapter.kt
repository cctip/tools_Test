package com.hash.tooltemplate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R

data class WhichCardItem(val imageRes1: Int, val imageRes2: Int)

class WhichCardAdapter(private val gridItems: List<WhichCardItem>) : RecyclerView.Adapter<WhichCardAdapter.WhichCardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhichCardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_whichcard, parent, false)
        return WhichCardHolder(view)
    }

    override fun onBindViewHolder(holder: WhichCardHolder, position: Int) {
        val gridItem = gridItems[position]

        holder.imageView1.setImageResource(gridItem.imageRes1)
        holder.imageView2.setImageResource(gridItem.imageRes2)
    }

    override fun getItemCount(): Int {
        return gridItems.size
    }

    inner class WhichCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.item_whichcardimg)
        val imageView2: ImageView = itemView.findViewById(R.id.item_whichcardimgbg)
    }
}
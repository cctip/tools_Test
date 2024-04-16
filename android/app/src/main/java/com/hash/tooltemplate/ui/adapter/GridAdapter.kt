package com.hash.tooltemplate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
data class GridItem(val imageRes1: Int, val imageRes2: Int)

class GridAdapter(private val gridItems: List<GridItem>) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memorgrid, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val gridItem = gridItems[position]

        holder.imageView1.setImageResource(gridItem.imageRes1)
        holder.imageView2.setImageResource(gridItem.imageRes2)
    }

    override fun getItemCount(): Int {
        return gridItems.size
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.item_memorimg)
        val imageView2: ImageView = itemView.findViewById(R.id.item_memorimgbg)
    }
}

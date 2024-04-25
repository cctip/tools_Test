package com.hash.tooltemplate.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import com.hash.tooltemplate.ui.activity.WhichCarComeActivity

data class WhichcarItem(val imageRes1: Int, val imageRes2: Int)
class WhichcarAdapter(private val whichcarItems: List<WhichcarItem>, private val outsideTextView: TextView,private val whichcarstart: TextView,private val context: Context) : RecyclerView.Adapter<WhichcarAdapter.WhichcarHolder>() {
    private var selectedPosition = -1
    private var lastClickedImageResourceId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WhichcarHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_whichcar, parent, false)
        return WhichcarHolder(view)
    }

    override fun onBindViewHolder(holder: WhichcarHolder, position: Int) {
        val gridItem = whichcarItems[position]

        if (selectedPosition == position) {
            holder.imageView1.scaleX = 1.7f
            holder.imageView1.scaleY = 1.7f
        } else {
            holder.imageView1.scaleX = 1.0f
            holder.imageView1.scaleY = 1.0f
        }

        holder.imageView1.setImageResource(gridItem.imageRes1)
        holder.imageView2.setImageResource(gridItem.imageRes2)

        holder.itemView.setOnClickListener {
            outsideTextView.setTextColor(Color.WHITE)
            outsideTextView.isClickable=true
            lastClickedImageResourceId =gridItem.imageRes1
            whichcarstart.setOnClickListener {
                val intent = Intent(context, WhichCarComeActivity::class.java)
                lastClickedImageResourceId?.let {
                    intent.putExtra("lastClickedImageResourceId", it)
                    context.startActivity(intent)
                }
            }
            setSelectedPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return whichcarItems.size
    }

    private fun setSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(position)
    }

    inner class WhichcarHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.item_whichcarimg)
        val imageView2: ImageView = itemView.findViewById(R.id.item_whichcarimgbg)

    }
}

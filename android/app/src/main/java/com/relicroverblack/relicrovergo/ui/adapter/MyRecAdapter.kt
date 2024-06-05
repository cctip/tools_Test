package com.relicroverblack.relicrovergo.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.ui.activity.DetailsActivity


data class MyItem(val imageResource: Int, val text1: String, val text2: String, val itemviewResource: Int)

class MyRecAdapter(private val items: List<MyItem>, private val shardCount: Int) : RecyclerView.Adapter<MyRecAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_myrec, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position, shardCount)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.itemmyrecimg)
        private val textView1: TextView = itemView.findViewById(R.id.itemmyrectittle)
        private val textView2: TextView = itemView.findViewById(R.id.itemmyreccontent)
        private val itemview: ImageView = itemView.findViewById(R.id.itemview)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.customProgressBar) // 假设布局中有一个 ProgressBar

        fun bind(item: MyItem, position: Int, shardCount: Int) {
            // 设置图片资源
            imageView.setImageResource(item.imageResource)
            // 设置两个文本
            textView1.text = item.text1
            textView2.text = item.text2
            itemview.setImageResource(item.itemviewResource)

            if (position == 0) {
                // 计算进度并设置进度条
                val progress = (shardCount * 10).coerceAtMost(100) // 确保进度不超过100%
                progressBar.progress = progress
                if(progress == 100){
                    itemView.setBackgroundResource(R.mipmap.myintent)
                    // 设置点击事件

                }

            }
        }
    }
}

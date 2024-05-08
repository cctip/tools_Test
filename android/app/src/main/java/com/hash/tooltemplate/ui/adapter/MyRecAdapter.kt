package com.hash.tooltemplate.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import com.hash.tooltemplate.ui.activity.DetailsActivity

data class MyItem(val imageResource: Int, val text1: String, val text2: String, val itemviewResource: Int)

class MyRecAdapter(private val items: List<MyItem>) : RecyclerView.Adapter<MyRecAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_myrec, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.itemmyrecimg)
        private val textView1: TextView = itemView.findViewById(R.id.itemmyrectittle)
        private val textView2: TextView = itemView.findViewById(R.id.itemmyreccontent)
        private val itemview: ImageView = itemView.findViewById(R.id.itemview)
        fun bind(item: MyItem) {
            // 设置图片资源
            imageView.setImageResource(item.imageResource)
            // 设置两个文本
            textView1.text = item.text1
            textView2.text = item.text2
            itemview.setImageResource(item.itemviewResource)
            if(position == 0){
                itemview.setOnClickListener {
                    val intent = Intent(itemView.context, DetailsActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}

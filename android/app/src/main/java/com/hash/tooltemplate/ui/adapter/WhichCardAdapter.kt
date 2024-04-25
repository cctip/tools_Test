package com.hash.tooltemplate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import java.util.Random

data class WhichCardItem(val imageRes1: Int, val imageRes2: Int)

class WhichCardAdapter(private val items: List<WhichCardItem>) : RecyclerView.Adapter<WhichCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_whichcard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.cardImage.setImageResource(item.imageRes1)
        holder.itemBgImage.setImageResource(item.imageRes2)

        // 为第一个图片设置点击事件
        holder.cardImage.setOnClickListener {
            // 随机选择要显示的图片
            val random = Random()
            val randomNumber = random.nextInt(12) // 生成 0 到 11 之间的随机数
            if (randomNumber < 8) {
                // 显示第一种图片
                holder.cardImage.setImageResource(R.mipmap.island)
            } else {
                // 显示第二种图片
                holder.cardImage.setImageResource(R.mipmap.bom)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage: ImageView = itemView.findViewById(R.id.item_whichcardimg)
        val itemBgImage: ImageView = itemView.findViewById(R.id.item_whichcardimgbg)

    }
}

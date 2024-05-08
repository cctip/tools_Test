package com.hash.tooltemplate.ui.adapter

import android.os.Handler
import android.util.Log
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import com.hash.tooltemplate.ui.activity.MemorActivity

interface DialogClickListener {
    fun onDeathDialogClick()
    fun onWinDialogClick()
}
class GridAdapter(private val items: List<GridItem>,private val dialogClickListener: DialogClickListener,private val context: Context) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    private var isLose = 0
    private var isWin = 0
    private var expandedPositions = mutableListOf<Int>()
    private var expanded = mutableListOf<Int>()
    private val clickedStates = BooleanArray(items.size) { false }
    private var isClickable = false
    private var currentRow = 0
    val memorstart = (context as MemorActivity).findViewById<TextView>(R.id.memorstart)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memorgrid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
        memorstart.setOnClickListener {

            memorstart.visibility=View.GONE
            // 初始放大第一行的一个图片
            memorstart.clearFocus()
            Handler().postDelayed({
                setClickable(true)
            }, 5500)
            Handler().postDelayed({
                expandNextRow()
            }, 1000)
        }
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
    fun setClickable(clickable: Boolean) {
        isClickable = clickable
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.item_memorimg)
        private val vibrator = itemView.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

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
                if (isClickable){
                    if ( expanded.contains(position)) {
                        isWin++
                        imageView.apply {
                            setImageResource(item.imageResourceId)
                            layoutParams = layoutParams.apply {
                                width = 280
                                height = 280
                            }
                            requestLayout()
                        }
                        if(isWin==4){
                            dialogClickListener.onWinDialogClick()
                        }
                    }else{
                        isLose++
                        // 判断设备是否支持震动
                        if (vibrator.hasVibrator()) {
                            // 定义震动模式
                            val pattern = longArrayOf(0, 100) // 震动1秒
                            // 创建VibrationEffect实例
                            val vibrationEffect = VibrationEffect.createWaveform(pattern, -1)
                            // 开始震动
                            vibrator.vibrate(vibrationEffect)
                        }
                        if(isLose==3){
                            dialogClickListener.onDeathDialogClick()
                        }
                    }
                }
            }
        }

    }
    private fun expandNextRow() {
        // 判断当前行是否超出范围
        if (currentRow >= 4) return

        // 随机选择当前行的一个图片进行放大
        val randomPosition = (currentRow * 3 until (currentRow + 1) * 3).random()
        expandItemAtPosition(randomPosition)
        // 一秒后缩小图片并执行下一行的放大操作
        Handler().postDelayed({
            resetExpandedPositions()
            currentRow++
            expandNextRow()
        }, 1000)
    }

}
data class GridItem(val imageResourceId: Int, val backgroundImageResourceId: Int)

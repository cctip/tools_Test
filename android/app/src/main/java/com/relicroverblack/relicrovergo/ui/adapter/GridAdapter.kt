package com.relicroverblack.relicrovergo.ui.adapter

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
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.ui.activity.MemorActivity

interface DialogClickListener {
    fun onDeathDialogClick()
    fun onWinDialogClick()
}
class GridAdapter(
    private val items: List<GridItem>,
    private val dialogClickListener: DialogClickListener,
    private val context: Context
) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    private var isLose = 0
    private var isWin = 0
    private var expandedPositions = mutableListOf<Int>()
    private var expanded = mutableListOf<Int>()
    private val clickedStates = BooleanArray(items.size) { false }
    private var isClickable = false
    private var currentRow = 0
    private val memorstart = (context as MemorActivity).findViewById<TextView>(R.id.memorstart)
    private val updateProgressListener: (Int) -> Unit = { (context as MemorActivity).updateProgress(it) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memorgrid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
        memorstart.setOnClickListener {
            memorstart.visibility = View.GONE
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
                setImageResource(item.imageResourceId)

                layoutParams = layoutParams.apply {
                    width = if (isExpanded(position)) 280 else 200
                    height = if (isExpanded(position)) 280 else 200
                }
                requestLayout()
            }
            itemView.setOnClickListener {
                Log.i("expandedPositions", expanded.toString())
                Log.i("position", position.toString())
                if (isClickable && !clickedStates[position]) {
                    clickedStates[position] = true // 标记为已点击
                    if (expanded.contains(position)) {
                        isWin++
                        updateProgressListener(isWin)
                        imageView.apply {
                            setImageResource(item.imageResourceId)
                            layoutParams = layoutParams.apply {
                                width = 280
                                height = 280
                            }
                            requestLayout()
                        }
                        if (isWin == 4) {
                            dialogClickListener.onWinDialogClick()
                        }
                    } else {
                        isLose++
                        if (vibrator.hasVibrator()) {
                            val pattern = longArrayOf(0, 100)
                            val vibrationEffect = VibrationEffect.createWaveform(pattern, -1)
                            vibrator.vibrate(vibrationEffect)
                        }
                        if (isLose == 3) {
                            dialogClickListener.onDeathDialogClick()
                        }
                    }
                }
            }
        }
    }

    private fun expandNextRow() {
        if (currentRow >= 4) return

        val randomPosition = (currentRow * 3 until (currentRow + 1) * 3).random()
        expandItemAtPosition(randomPosition)
        Handler().postDelayed({
            resetExpandedPositions()
            currentRow++
            expandNextRow()
        }, 1000)
    }
}


data class GridItem(val imageResourceId: Int, val backgroundImageResourceId: Int)

package com.relicroverblack.relicrovergo.ui.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.relicroverblack.relicrovergo.R
import java.util.Random

data class WhichCardItem(val imageRes1: Int, val imageRes2: Int,var isClicked: Boolean = false)
interface DialogClickListenerCard {
    fun onDeathDialogClick()
    fun onWinDialogClick()
    fun onDeathSize()
}
class WhichCardAdapter(private val items: List<WhichCardItem>,private val dialogClickListener: DialogClickListenerCard) : RecyclerView.Adapter<WhichCardAdapter.ViewHolder>() {
    private var DeathClick = 0
    private var WinClick = 0
    private val clickedFlags = BooleanArray(items.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_whichcard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.cardImage.setImageResource(item.imageRes1)
        holder.itemBgImage.setImageResource(item.imageRes2)

        holder.cardImage.setOnClickListener {
            if (!clickedFlags[position]) {
                // 创建翻转动画
                val animator1 = ObjectAnimator.ofFloat(holder.cardImage, "rotationY", 0f, 90f).apply {
                    duration = 300
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            // 随机选择要显示的图片
                            val random = Random()
                            val randomNumber = random.nextInt(12) // 生成 0 到 11 之间的随机数
                            if (randomNumber < 8) {
                                // 显示第一种图片
                                holder.cardImage.setImageResource(R.mipmap.island)
                                WinClick++
                                if (WinClick == 7) {
                                    dialogClickListener.onWinDialogClick()
                                }
                            } else {
                                // 显示第二种图片
                                holder.cardImage.setImageResource(R.mipmap.bom)
                                DeathClick++
                                dialogClickListener.onDeathSize()
                                if (DeathClick == 3) {
                                    dialogClickListener.onDeathDialogClick()
                                }
                            }
                            clickedFlags[position] = true
                        }
                    })
                }

                val animator2 = ObjectAnimator.ofFloat(holder.cardImage, "rotationY", 90f, 180f).apply {
                    duration = 300
                }

                val animatorSet = AnimatorSet()
                animatorSet.playSequentially(animator1, animator2)

                // 为背景图片创建同步翻转动画
                val bgAnimator1 = ObjectAnimator.ofFloat(holder.itemBgImage, "rotationY", 0f, 90f).apply {
                    duration = 300
                }

                val bgAnimator2 = ObjectAnimator.ofFloat(holder.itemBgImage, "rotationY", 90f, 180f).apply {
                    duration = 300
                }

                val bgAnimatorSet = AnimatorSet()
                bgAnimatorSet.playSequentially(bgAnimator1, bgAnimator2)

                // 同步播放两个动画集合
                val totalAnimatorSet = AnimatorSet()
                totalAnimatorSet.playTogether(animatorSet, bgAnimatorSet)
                totalAnimatorSet.start()
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

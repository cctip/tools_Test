package com.relicroverblack.relicrovergo.widgets

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.utils.DisplayUtils

import kotlin.random.Random

class LuckyView : View {

    companion object {
        const val MAX_DRAW_COUNT = 3
        const val ICON_SIZE = 9

        //图标的间距不规则
        val ITEM_MARGINS = intArrayOf(24, 24, 24, 41, 24, 24, 33, 24)
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val source: Bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.decorate)

    private val bitmapCache: MutableMap<Int, Bitmap> = mutableMapOf()
    private val offsets: FloatArray = floatArrayOf(0f, 0f, 0f)
    private val itemHorizontalSpace: Float = DisplayUtils.dp2px(context, 38f)
    private val itemVerticalSpace: Float = DisplayUtils.dp2px(context, 12f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var itemSize: Float = 0f
    private var cycleOffset: Float = 0f
    var onSpinEnd:((Int,Int,Int)->Unit)? = null


    private val animatorSet:AnimatorSet = AnimatorSet().apply {
        addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                val i0 = getRealIconIndex(0)
                val i1 = getRealIconIndex(1)
                val i2 = getRealIconIndex(2)
                onSpinEnd?.invoke(i0,i1,i2)
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })
    }

    //每次显示都打乱位置
    private val arrangeList: List<MutableList<Int>> = mutableListOf<MutableList<Int>>().apply {
        add(randomRange())
        add(randomRange())
        add(randomRange())
    }

    private fun randomRange(): MutableList<Int> {
        val list = mutableListOf<Int>().apply {
            for (i in 0 until ICON_SIZE) {
                add(i)
            }
        }
        shuffle(list)
        return list
    }

    private fun shuffle(list: MutableList<Int>) {
        for (i in 0..100) {
            val n = Random.nextInt(ICON_SIZE)
            val m = Random.nextInt(ICON_SIZE)
            if (n == m) continue
            val t = list[n]
            list[n] = list[m]
            list[m] = t
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawColumn(canvas, 0)
        drawColumn(canvas, 1)
        drawColumn(canvas, 2)
    }

    fun startPin() {
        if (animatorSet.isRunning) return
        animatorSet.childAnimations.clear()
        animatorSet.play(genTranslateAnimator(0))
        animatorSet.play(genTranslateAnimator(1))
        animatorSet.play(genTranslateAnimator(2))
        animatorSet.start()
    }

    private fun genTranslateAnimator(column: Int):ValueAnimator {
        val curOffset = offsets[column] % cycleOffset
        //移动的offset 以单个item计算，这样不用修正偏移
        val itemOffset = itemSize + itemVerticalSpace
        //移动4圈+随机个item距离
        val offset = ICON_SIZE * itemOffset * 4 + Random.nextInt(ICON_SIZE) * itemOffset
        return ObjectAnimator.ofFloat(curOffset,curOffset+offset).apply {
            duration = 4000L
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                val v = it.animatedValue as Float
                if(v>0.1f){
                    offsets[column] = if(v >= curOffset)v else curOffset
                    if(column == 1)Log.d("Offset","$v,\t${offsets[column]}")
                    //TODO 避免重绘
                    invalidate()
                }
            }
        }
    }

    private fun getRealIconIndex(column: Int):Int{
        val offsetOfOne = offsets[column] % cycleOffset
        var midIndex = (offsetOfOne / (itemSize + itemVerticalSpace)).toInt()
        var offset = offsetOfOne / (itemSize + itemVerticalSpace)
        Log.d("AnimEnd","$offset")
        var index = ensureIndex(midIndex)
        return arrangeList[column][index]
    }

    private fun drawColumn(canvas: Canvas?, column: Int) {
        itemSize =
            (width - paddingStart - paddingEnd - (itemHorizontalSpace) * (offsets.size - 1)) / offsets.size
        val start = paddingStart + (itemSize + itemHorizontalSpace) * column
        //绘制三个可视的bitmap

        //从中间开始绘制第一个item
        val mid = (height - paddingTop - paddingBottom) / 2 + paddingTop

        //一圈的距离
        cycleOffset = (itemSize + itemVerticalSpace) * ICON_SIZE
        //实际的偏移
        val offsetOfOne = offsets[column] % cycleOffset
        //居中的item
        var midIndex = (offsetOfOne / (itemSize + itemVerticalSpace)).toInt()
        var offset = offsetOfOne % (itemSize + itemVerticalSpace)
        //向上绘制
        for (i in -MAX_DRAW_COUNT..MAX_DRAW_COUNT) {
            var iconIndex = ensureIndex(midIndex + i)
            iconIndex = arrangeList[column][iconIndex]
            val t = offset - (itemSize + itemVerticalSpace) * i + DisplayUtils.dp2px(context, 10f) // 加上10dp的距离
            val b = getBitmapAt(iconIndex, itemSize)
            canvas?.drawBitmap(b, start, mid - itemSize / 2 + t, paint)
        }
    }


    private fun ensureIndex(index: Int): Int {
        var i = index
        while (i < 0) {
            i = index + ICON_SIZE
        }
        while (i >= ICON_SIZE) {
            i -= ICON_SIZE
        }
        return i
    }


    private fun getBitmapAt(index: Int, size: Float): Bitmap {
        // 假设您有九张自定义图片，并且将它们存储在一个数组或列表中
        val customBitmaps = arrayOf(
            R.mipmap.cloud ,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud
        )

        // 检查索引是否在合法范围内
        if (index !in 0 until customBitmaps.size) {
            throw IndexOutOfBoundsException("Index must be between 0 and ${customBitmaps.size - 1}")
        }

        // 从资源中获取位图
        val sourceBitmap = BitmapFactory.decodeResource(context.resources, customBitmaps[index])

        // 然后按照您之前的逻辑进行缩放处理等
        // 注意：这里假设您的资源是合适的，没有进行额外的处理
        // 如果需要缩放等其他处理，请按照您的需求修改此处

        // 返回处理后的位图
        return sourceBitmap
    }



    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        bitmapCache.clear()
        animatorSet.cancel()
        animatorSet.childAnimations.clear()
        onSpinEnd = null
    }
}
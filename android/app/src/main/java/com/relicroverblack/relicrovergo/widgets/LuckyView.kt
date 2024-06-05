package com.relicroverblack.relicrovergo.widgets

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.ui.dialog.DeathDialog
import com.relicroverblack.relicrovergo.ui.dialog.WinBingDialog
import com.relicroverblack.relicrovergo.ui.dialog.WinDialog
import com.relicroverblack.relicrovergo.utils.DisplayUtils

import kotlin.random.Random

class LuckyView : View {
    var customBitmaps: Array<Int> = arrayOf(
        R.mipmap.cloud,
        R.mipmap.cloud,
        R.mipmap.cloud,
        R.mipmap.cloud,
        R.mipmap.cloud,
        R.mipmap.cloud,
        R.mipmap.cloud,
        R.mipmap.cloud,
        R.mipmap.cloud
    )

    companion object {
        const val MAX_DRAW_COUNT = 3
        const val ICON_SIZE = 9
        const val PREFS_NAME = "LuckyViewPrefs"
        const val BITMAP_PREFIX = "bitmap_"
        const val SHARD_KEY = "shard"
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    private val bitmapCache: MutableMap<Int, Bitmap> = mutableMapOf()
    private val offsets: FloatArray = floatArrayOf(0f, 0f, 0f)
    private val itemHorizontalSpace: Float = DisplayUtils.dp2px(context, 38f)
    private val itemVerticalSpace: Float = DisplayUtils.dp2px(context, 12f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var itemSize: Float = 0f
    private var cycleOffset: Float = 0f
    var onSpinEnd: ((Int, Int, Int) -> Unit)? = null

    private val animatorSet: AnimatorSet = AnimatorSet().apply {
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                val i0 = getRealIconIndex(0)
                val i1 = getRealIconIndex(1)
                val i2 = getRealIconIndex(2)
                onSpinEnd?.invoke(i0, i1, i2)

                // 获取图片资源 ID
                val resId0 = customBitmaps[i0]
                val resId1 = customBitmaps[i1]
                val resId2 = customBitmaps[i2]

                // 检查是否中间一横排的图片相同
                if (resId0 == resId1 && resId1 == resId2 && resId0 != 2131558409 && resId1 != 2131558409 && resId2 != 2131558409) {
                    Log.i("resId0",resId0.toString())
                    // 中间一横排图片相同，显示弹窗
                    (context as? Activity)?.runOnUiThread {
                        Handler().postDelayed({
                            showWinDialog()
                        }, 200)
                    }
                }else{
                    (context as? Activity)?.runOnUiThread {
                        Handler().postDelayed({
                            showDeathDialog()
                        }, 200)
                    }
                }
            }
            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
    fun resetImages() {
        customBitmaps = arrayOf(
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud,
            R.mipmap.cloud
        )
        bitmapCache.clear()
        saveBitmapPreferencesDouble() // 保存重置后的图片
        invalidate() // 重新绘制视图
    }
    // 每次显示都打乱位置
    private val arrangeList: List<MutableList<Int>> = mutableListOf<MutableList<Int>>().apply {
        add(randomRange())
        add(randomRange())
        add(randomRange())
    }
    // 重载保存图片的方法来保存所有图片
    private fun saveBitmapPreferencesDouble() {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            customBitmaps.forEachIndexed { index, resId ->
                putInt(BITMAP_PREFIX + index, resId)
            }
            apply()
        }
    }
    init {
        loadBitmapPreferences()
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawColumn(canvas, 0)
        drawColumn(canvas, 1)
        drawColumn(canvas, 2)
    }
    fun startPin() {
        if (animatorSet.isRunning) return
        animatorSet.childAnimations.clear()
        animatorSet.playTogether(
            genTranslateAnimator(0),
            genTranslateAnimator(1),
            genTranslateAnimator(2)
        )
        animatorSet.start()
    }
    private fun genTranslateAnimator(column: Int): ValueAnimator {
        val curOffset = offsets[column] % cycleOffset
        //移动的offset 以单个item计算，这样不用修正偏移
        val itemOffset = itemSize + itemVerticalSpace
        //移动4圈+随机个item距离
        val offset = ICON_SIZE * itemOffset * 4 + Random.nextInt(ICON_SIZE) * itemOffset
        return ObjectAnimator.ofFloat(curOffset, curOffset + offset).apply {
            duration = 4000L
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                val v = it.animatedValue as Float
                offsets[column] = if (v >= curOffset) v else curOffset
                // 避免重绘
                invalidate()
            }
        }
    }
    fun updateBitmapAt(index: Int, newResId: Int) {
        if (index in customBitmaps.indices) {
            customBitmaps[index] = newResId
            bitmapCache.clear()
            saveBitmapPreferences(index, newResId)
            invalidate()// 重新绘制视图
        }
    }
    private fun getRealIconIndex(column: Int): Int {
        val offsetOfOne = offsets[column] % cycleOffset
        val midIndex = (offsetOfOne / (itemSize + itemVerticalSpace)).toInt()
        return arrangeList[column][ensureIndex(midIndex)]
    }
    private fun drawColumn(canvas: Canvas, column: Int) {
        itemSize = (width - paddingStart - paddingEnd - (itemHorizontalSpace) * (offsets.size - 1)) / offsets.size
        val start = paddingStart + (itemSize + itemHorizontalSpace) * column
        //绘制三个可视的bitmap
        //从中间开始绘制第一个item
        val mid = (height - paddingTop - paddingBottom) / 2 + paddingTop //一圈的距离
        cycleOffset = (itemSize + itemVerticalSpace) * ICON_SIZE //实际的偏移
        val offsetOfOne = offsets[column] % cycleOffset //居中的item
        val midIndex = (offsetOfOne / (itemSize + itemVerticalSpace)).toInt()
        val offset = offsetOfOne % (itemSize + itemVerticalSpace) //向上绘制
        for (i in -MAX_DRAW_COUNT..MAX_DRAW_COUNT) {
            var iconIndex = ensureIndex(midIndex + i)
            iconIndex = arrangeList[column][iconIndex]
            val t = offset - (itemSize + itemVerticalSpace) * i + DisplayUtils.dp2px(context, 10f)// 加上10dp的距离
            val b = getBitmapAt(iconIndex, itemSize)
            canvas.drawBitmap(b, start, mid - itemSize / 2 + t, paint)
        }
    }
    private fun ensureIndex(index: Int): Int {
        var i = index
        while (i < 0) {
            i += ICON_SIZE
        }
        while (i >= ICON_SIZE) {
            i -= ICON_SIZE
        }
        return i
    }
    private fun getBitmapAt(index: Int, size: Float): Bitmap {
        return bitmapCache.getOrPut(index) {
            val sourceBitmap = BitmapFactory.decodeResource(context.resources, customBitmaps[index])
            val scale = 0.8f// 缩放比例，0.8表示缩小为原来的80%
            val width = (sourceBitmap.width * scale).toInt()
            val height = (sourceBitmap.height * scale).toInt()
            Bitmap.createScaledBitmap(sourceBitmap, width, height, true)
        }
    }
    private fun saveBitmapPreferences(index: Int, resId: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(BITMAP_PREFIX + index, resId).apply()
    }
    private fun loadBitmapPreferences() {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        for (i in customBitmaps.indices) {
            customBitmaps[i] = prefs.getInt(BITMAP_PREFIX + i, customBitmaps[i])
        }
    }
    private fun showWinDialog() {
        val winDialog = WinBingDialog(context)
        winDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        winDialog.setCanceledOnTouchOutside(false)
        winDialog.show()
        // 恢复所有图片到初始状态
        resetImages()
        // 更新 shard 变量
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentShard = prefs.getInt(SHARD_KEY, 0)
        prefs.edit().putInt(SHARD_KEY, currentShard + 1).apply()
    }
    private fun showDeathDialog() {
        val winDialog = DeathDialog(context)
        winDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        winDialog.setCanceledOnTouchOutside(false)
        winDialog.show()
    }
    fun areAllImagesChanged(): Boolean {
        return customBitmaps.all { it != R.mipmap.cloud }
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        bitmapCache.clear()
        animatorSet.cancel()
        animatorSet.childAnimations.clear()
        onSpinEnd = null
    }
}

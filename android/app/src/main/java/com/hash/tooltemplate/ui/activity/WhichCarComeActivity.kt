package com.hash.tooltemplate.ui.activity

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityWhichCarComeBinding
import com.hash.tooltemplate.ui.adapter.PlayCarAdapter
import com.hash.tooltemplate.ui.dialog.DeathDialog
import com.hash.tooltemplate.ui.dialog.WinDialog
import java.util.Timer
import java.util.TimerTask

class WhichCarComeActivity : BaseActivity<ActivityWhichCarComeBinding>() {
    private lateinit var imagePagerAdapter: PlayCarAdapter
    private var currentIndex = 0
    private var lastPosition = 0
    private val handler = Handler(Looper.getMainLooper())
    private var autoScrollTimer: Timer? = null
    private var lastClickedImageResourceId = 0


    override fun getViewBinding(): ActivityWhichCarComeBinding = ActivityWhichCarComeBinding.inflate(layoutInflater)

    override fun initData() {
        lastClickedImageResourceId = intent.getIntExtra("lastClickedImageResourceId", 0)
        binding.carcomeimg.setImageResource(lastClickedImageResourceId)
        binding.pager.isUserInputEnabled = false
        Log.i("AutoScroll",lastClickedImageResourceId.toString())
        binding.whichcarcomeback.setOnClickListener {
            finish()
        }

        val imageList = arrayOf(
            R.mipmap.bluecar,
            R.mipmap.helicopter,
            R.mipmap.orangecar,
            R.mipmap.yellowcar,
            R.mipmap.bus,
            R.mipmap.aeroplane
        )
        // 打乱图片顺序
        val shuffledImageList = imageList.toList().shuffled().toTypedArray()
        val middlePosition = Int.MAX_VALUE / 2
        binding.pager.setCurrentItem(middlePosition, false)
        imagePagerAdapter = PlayCarAdapter(this, shuffledImageList)
        binding.pager.adapter = imagePagerAdapter
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentIndex = position % imagePagerAdapter.itemCount
                lastPosition = position
            }
        })
        Handler().postDelayed({
            disableAllControls()
            startAutoScroll()
        },1000)
    }
    private fun disableAllControls() {
        setControlsEnabledExceptBackButton(binding.root, false)
    }
    private fun setControlsEnabledExceptBackButton(view: View, enabled: Boolean) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                if (child != binding.whichcarcomeback) {
                    setControlsEnabled(child, enabled)
                }
            }
        } else {
            if (view != binding.whichcarcomeback) {
                view.isEnabled = enabled
            }
        }
    }
    private fun startAutoScroll() {
        autoScrollTimer = Timer()
        autoScrollTimer?.scheduleAtFixedRate(object : TimerTask() {
            private var elapsedTime = 0L
            override fun run() {
                // 连续平移
                currentIndex = currentIndex % imagePagerAdapter.itemCount
                val nextPage = (currentIndex + 1) % imagePagerAdapter.itemCount
                Log.d("AutoScroll", "Next Page: $nextPage")
                runOnUiThread {
                    binding.pager.setCurrentItem(nextPage, true)
                    currentIndex = nextPage
                }
                elapsedTime += 700 // 1000毫秒即1秒
                if (elapsedTime >= 6000) {
                    autoScrollTimer?.cancel()
                    autoScrollTimer = null
                    runOnUiThread {
                        handleLastPosition()
                    }
                }
            }
        }, 0, 700) // 第一个参数表示延迟0毫秒开始执行，第二个参数表示每隔700毫秒执行一次
    }
    private fun handleLastPosition() {
        // 获取当前显示的图片索引
        val currentImageIndex = currentIndex % imagePagerAdapter.itemCount
        // 从适配器中获取对应的图片ID
        val currentImageResourceId = imagePagerAdapter.getImageResourceId(currentImageIndex)
        Log.i("AutoScroll", currentImageResourceId.toString())
        if(lastClickedImageResourceId==currentImageResourceId){
            Handler().postDelayed({
                onWin()
            },1000)
        }else{
            Handler().postDelayed({
                onDeaful()
            },1000)
        }
    }
    fun onWin(){
        Handler().postDelayed({
            // 获取 WindowManager
            val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
            // 创建半透明背景
            val grayBackground = View(this@WhichCarComeActivity)
            grayBackground.setBackgroundColor(Color.parseColor("#80000000")) // 半透明灰色
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            layoutParams.format = PixelFormat.TRANSLUCENT
            windowManager.addView(grayBackground, layoutParams)
            val winDialog = WinDialog( context = this@WhichCarComeActivity,)
            winDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            winDialog.setCanceledOnTouchOutside(false) // 设置点击外部区域不关闭弹窗
            winDialog.show()
        }, 1000L)
    }
    fun onDeaful(){
        // 获取 WindowManager
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        // 创建半透明背景
        val grayBackground = View(this@WhichCarComeActivity)
        grayBackground.setBackgroundColor(Color.parseColor("#80000000")) // 半透明灰色
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
        layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams.format = PixelFormat.TRANSLUCENT
        windowManager.addView(grayBackground, layoutParams)
        val defeatDialog = DeathDialog( context = this@WhichCarComeActivity)
        defeatDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        defeatDialog.setCanceledOnTouchOutside(false) // 设置点击外部区域不关闭弹窗
        defeatDialog.show()
    }
    override fun onDestroy() {
        // 停止计时器并移除所有未执行的任务
        autoScrollTimer?.cancel()
        autoScrollTimer = null
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
    private fun setControlsEnabled(view: View, enabled: Boolean) {
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                setControlsEnabled(child, enabled)
            }
        } else {
            view.isEnabled = enabled
        }
    }
}
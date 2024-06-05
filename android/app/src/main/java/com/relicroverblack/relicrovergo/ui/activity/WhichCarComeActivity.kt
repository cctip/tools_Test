package com.relicroverblack.relicrovergo.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseActivity
import com.relicroverblack.relicrovergo.databinding.ActivityWhichCarComeBinding
import com.relicroverblack.relicrovergo.ui.adapter.PlayCarAdapter
import com.relicroverblack.relicrovergo.ui.dialog.DeathDialog
import com.relicroverblack.relicrovergo.ui.dialog.WinDialog
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
        Log.i("AutoScroll", lastClickedImageResourceId.toString())
        binding.whichcarcomeback.setOnClickListener {
            finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val imageList = arrayOf(
            R.mipmap.bluecar,
            R.mipmap.helicopter,
            R.mipmap.orangecar,
            R.mipmap.yellowcar,
            R.mipmap.bus,
            R.mipmap.aeroplane
        )
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

        // 设置页面间距
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.page_margin)
        val pagerWidth = resources.getDimensionPixelOffset(R.dimen.pager_width)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pagerWidth - pageMarginPx

        binding.pager.setPageTransformer { page, position ->
            page.translationX = -offsetPx * position
        }

        Handler().postDelayed({
            disableAllControls()
            startAutoScroll()
        }, 1000)
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
                currentIndex = currentIndex % imagePagerAdapter.itemCount
                val nextPage = (currentIndex + 1) % imagePagerAdapter.itemCount
                Log.d("AutoScroll", "Next Page: $nextPage")
                runOnUiThread {
                    binding.pager.setCurrentItem(nextPage, true)
                    currentIndex = nextPage
                }
                elapsedTime += 700
                if (elapsedTime >= 6000) {
                    autoScrollTimer?.cancel()
                    autoScrollTimer = null
                    runOnUiThread {
                        handleLastPosition()
                    }
                }
            }
        }, 0, 700)
    }

    private fun handleLastPosition() {
        val currentImageIndex = currentIndex % imagePagerAdapter.itemCount
        val currentImageResourceId = imagePagerAdapter.getImageResourceId(currentImageIndex)
        Log.i("AutoScroll", currentImageResourceId.toString())
        if (lastClickedImageResourceId == currentImageResourceId) {
            Handler().postDelayed({
                onWin()
            }, 1000)
        } else {
            Handler().postDelayed({
                onDeaful()
            }, 1000)
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
            val images = listOf(R.mipmap.bussmall, R.mipmap.fly, R.mipmap.smallbluecar)
            val winDialog = WinDialog( context = this@WhichCarComeActivity,images)
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
package com.hash.tooltemplate.ui.activity

import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityWhichCarComeBinding
import com.hash.tooltemplate.ui.adapter.PlayCarAdapter
import java.util.Timer
import java.util.TimerTask

class WhichCarComeActivity : BaseActivity<ActivityWhichCarComeBinding>() {
    private lateinit var imagePagerAdapter: PlayCarAdapter
    private var currentIndex = 0
    private var lastPosition = 0
    private var autoScrollTimer: Timer? = null
    override fun getViewBinding(): ActivityWhichCarComeBinding = ActivityWhichCarComeBinding.inflate(layoutInflater)

    override fun initData() {
        val lastClickedImageResourceId = intent.getIntExtra("lastClickedImageResourceId", 0)
        binding.carcomeimg.setImageResource(lastClickedImageResourceId)
        binding.pager.isUserInputEnabled=false
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
        // 设置初始位置为足够大的值的中间位置
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
        Log.i("AutoScroll", "Current Image Resource ID: $currentImageResourceId")
        //粉色93红95绿96黄94蓝97紫92

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
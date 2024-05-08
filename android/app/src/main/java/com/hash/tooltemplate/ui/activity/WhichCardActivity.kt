package com.hash.tooltemplate.ui.activity

import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityWhichCardBinding
import com.hash.tooltemplate.ui.adapter.DialogClickListener
import com.hash.tooltemplate.ui.adapter.DialogClickListenerCard
import com.hash.tooltemplate.ui.adapter.WhichCardAdapter
import com.hash.tooltemplate.ui.adapter.WhichCardItem
import com.hash.tooltemplate.ui.dialog.DeathDialog
import com.hash.tooltemplate.ui.dialog.WinDialog
import java.util.Objects

class WhichCardActivity : BaseActivity<ActivityWhichCardBinding>() {
    private lateinit var recyclerView: RecyclerView

    override fun getViewBinding(): ActivityWhichCardBinding = ActivityWhichCardBinding.inflate(layoutInflater)

    override fun initData() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        val whichcardItems = listOf(
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
            WhichCardItem(R.mipmap.mysteriouscard, R.mipmap.memoritembg),
        )
        val adapter = WhichCardAdapter(whichcardItems, object : DialogClickListenerCard {
            override fun onDeathDialogClick() {
                val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
                // 创建半透明背景
                val grayBackground = View(this@WhichCardActivity)
                grayBackground.setBackgroundColor(Color.parseColor("#80000000")) // 半透明灰色
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
                layoutParams.flags =
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                layoutParams.format = PixelFormat.TRANSLUCENT
                windowManager.addView(grayBackground, layoutParams)
                val defeatDialog = DeathDialog(this@WhichCardActivity)
                defeatDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                defeatDialog.setCanceledOnTouchOutside(false) // 设置点击外部区域不关闭弹窗
                defeatDialog.show()
            }
            override fun onWinDialogClick() {
                // 获取 WindowManager
                val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
                // 创建半透明背景
                val grayBackground = View(this@WhichCardActivity)
                grayBackground.setBackgroundColor(Color.parseColor("#80000000")) // 半透明灰色
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
                layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                layoutParams.format = PixelFormat.TRANSLUCENT
                windowManager.addView(grayBackground, layoutParams)
                val winDialog = WinDialog(this@WhichCardActivity)
                winDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                winDialog.setCanceledOnTouchOutside(false) // 设置点击外部区域不关闭弹窗
                winDialog.show()
            }
        })
        recyclerView.adapter = adapter
        binding.memorback.setOnClickListener {
            finish()
        }
    }
    fun calculateProgress(value: Int): Int {
        //1为33进度为100
        return if (value == 3) {
            100
        } else {
            (value * 33)
        }
    }
}
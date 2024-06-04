package com.relicroverblack.relicrovergo.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseActivity
import com.relicroverblack.relicrovergo.databinding.ActivityWhichCardBinding
import com.relicroverblack.relicrovergo.ui.adapter.DialogClickListenerCard
import com.relicroverblack.relicrovergo.ui.adapter.WhichCardAdapter
import com.relicroverblack.relicrovergo.ui.adapter.WhichCardItem
import com.relicroverblack.relicrovergo.ui.dialog.DeathDialog
import com.relicroverblack.relicrovergo.ui.dialog.WinDialog

class WhichCardActivity : BaseActivity<ActivityWhichCardBinding>() {
    private lateinit var recyclerView: RecyclerView

    override fun getViewBinding(): ActivityWhichCardBinding =
        ActivityWhichCardBinding.inflate(layoutInflater)

    override fun initData() {
        binding.ProgressBar.progress = 100
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.checkpointtext.text = "3"
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
                val rootView = findViewById<View>(android.R.id.content)
                setViewsNonClickable(rootView)
                Handler().postDelayed({
                    val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
                    val grayBackground = View(this@WhichCardActivity).apply {
                        setBackgroundColor(Color.parseColor("#80000000")) // 半透明灰色
                    }
                    val layoutParams = WindowManager.LayoutParams().apply {
                        width = WindowManager.LayoutParams.MATCH_PARENT
                        height = WindowManager.LayoutParams.MATCH_PARENT
                        type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
                        flags =
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        format = PixelFormat.TRANSLUCENT
                    }
                    windowManager.addView(grayBackground, layoutParams)

                    val defeatDialog = DeathDialog(this@WhichCardActivity).apply {
                        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        setCanceledOnTouchOutside(false) // 设置点击外部区域不关闭弹窗
                    }
                    defeatDialog.show()
                }, 500)
            }

            override fun onWinDialogClick() {
                val rootView = findViewById<View>(android.R.id.content)
                setViewsNonClickable(rootView)
                Handler().postDelayed({
                    val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
                    val grayBackground = View(this@WhichCardActivity).apply {
                        setBackgroundColor(Color.parseColor("#80000000")) // 半透明灰色
                    }
                    val layoutParams = WindowManager.LayoutParams().apply {
                        width = WindowManager.LayoutParams.MATCH_PARENT
                        height = WindowManager.LayoutParams.MATCH_PARENT
                        type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
                        flags =
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        format = PixelFormat.TRANSLUCENT
                    }
                    windowManager.addView(grayBackground, layoutParams)

                    val winDialog = WinDialog(this@WhichCardActivity).apply {
                        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        setCanceledOnTouchOutside(false) // 设置点击外部区域不关闭弹窗
                    }
                    winDialog.show()
                }, 500)
            }

            override fun onDeathSize() {
                val newProgress = binding.ProgressBar.progress - (binding.ProgressBar.max / 3)
                binding.ProgressBar.progress = newProgress

                // 更新 TextView 文本
                val remainingLives = binding.checkpointtext.text.toString().toInt() - 1
                binding.checkpointtext.text = remainingLives.toString()

                // 检查剩余生命值，可能需要处理游戏结束逻辑
                if (remainingLives <= 0) {
                    // 触发游戏失败逻辑，例如显示失败对话框
                    onDeathDialogClick()
                }
            }
        })
        recyclerView.adapter = adapter

        binding.memorback.setOnClickListener {
            finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    // 禁用点击事件
    fun setViewsNonClickable(view: View) {
        view.isClickable = false
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                setViewsNonClickable(view.getChildAt(i))
            }
        }
    }
}
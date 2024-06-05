package com.relicroverblack.relicrovergo.ui.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseActivity
import com.relicroverblack.relicrovergo.databinding.ActivityMemorBinding
import com.relicroverblack.relicrovergo.ui.adapter.DialogClickListener
import com.relicroverblack.relicrovergo.ui.adapter.GridAdapter
import com.relicroverblack.relicrovergo.ui.adapter.GridItem
import com.relicroverblack.relicrovergo.ui.dialog.DeathDialog
import com.relicroverblack.relicrovergo.ui.dialog.WinDialog
import com.relicroverblack.relicrovergo.ui.fragment.HorizontalAdapter

class MemorActivity : BaseActivity<ActivityMemorBinding>(), DialogClickListener {
    private lateinit var horadapter: HorizontalAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GridAdapter
    private var currentRow = 3

    override fun getViewBinding(): ActivityMemorBinding = ActivityMemorBinding.inflate(layoutInflater)

    override fun initData() {
        binding.memorstart.isClickable = false
        Handler().postDelayed({
            binding.oneblacktext.visibility = View.GONE
            binding.oneblackbg.visibility = View.GONE
            binding.memorstart.isClickable = true
        }, 2000)
        binding.recyclerView.isClickable = false
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.ProgressBar.max = 100
        binding.ProgressBar.progress = 100
        binding.textView.text = "3"
        val gridItems = listOf(
            GridItem(R.mipmap.memorblue, R.mipmap.memoritembg),
            GridItem(R.mipmap.memorcyan, R.mipmap.memoritembg),
            GridItem(R.mipmap.memoryelow, R.mipmap.memoritembg),
            GridItem(R.mipmap.memorgolden, R.mipmap.memoritembg),
            GridItem(R.mipmap.memorwhite, R.mipmap.memoritembg),
            GridItem(R.mipmap.memororange, R.mipmap.memoritembg),
            GridItem(R.mipmap.memorcyan, R.mipmap.memoritembg),
            GridItem(R.mipmap.memorblue, R.mipmap.memoritembg),
            GridItem(R.mipmap.memororange, R.mipmap.memoritembg),
            GridItem(R.mipmap.memorwhite, R.mipmap.memoritembg),
            GridItem(R.mipmap.memorgolden, R.mipmap.memoritembg),
            GridItem(R.mipmap.memoryelow, R.mipmap.memoritembg),
        )
        adapter = GridAdapter(gridItems, object : DialogClickListener {
            override fun onDeathDialogClick() {
                val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
                // 创建半透明背景
                val grayBackground = View(this@MemorActivity)
                grayBackground.setBackgroundColor(Color.parseColor("#80000000")) // 半透明灰色
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
                layoutParams.flags =
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                layoutParams.format = PixelFormat.TRANSLUCENT
                windowManager.addView(grayBackground, layoutParams)
                val defeatDialog = DeathDialog(this@MemorActivity)
                defeatDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                defeatDialog.setCanceledOnTouchOutside(false) // 设置点击外部区域不关闭弹窗
                defeatDialog.show()
            }

            override fun onWinDialogClick() {
                // 获取 WindowManager
                val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
                // 创建半透明背景
                val grayBackground = View(this@MemorActivity)
                grayBackground.setBackgroundColor(Color.parseColor("#80000000")) // 半透明灰色
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
                layoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                layoutParams.format = PixelFormat.TRANSLUCENT
                windowManager.addView(grayBackground, layoutParams)
                val images = listOf(R.mipmap.memorcyan, R.mipmap.memorwhite, R.mipmap.memoryelow,R.mipmap.memorblue)
                val winDialog = WinDialog(this@MemorActivity,images)
                winDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                winDialog.setCanceledOnTouchOutside(false) // 设置点击外部区域不关闭弹窗
                winDialog.show()
            }
        },this)

        recyclerView.adapter = adapter
        binding.memorback.setOnClickListener {
            finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
    fun updateProgress(isLose: Int) {
        // Assuming max progress is 4
        val progress = 100- (isLose * (binding.ProgressBar.max / 3))
        binding.ProgressBar.progress = progress
    }
    override fun onDeathDialogClick() {

    }

    fun updateTextView(isLose: Int) {
        // Assuming initial value is 3 and decreases by 1 for each `isLose`
        val remainingLives = 3 - isLose
        binding.textView.text = remainingLives.toString()
    }
    override fun onWinDialogClick() {

    }
}

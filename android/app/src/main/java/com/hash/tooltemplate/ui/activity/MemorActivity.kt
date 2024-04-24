package com.hash.tooltemplate.ui.activity

import android.os.Handler
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityMemorBinding
import com.hash.tooltemplate.ui.adapter.GridAdapter
import com.hash.tooltemplate.ui.adapter.GridItem
import com.hash.tooltemplate.widgets.CustomItemAnimator

class MemorActivity : BaseActivity<ActivityMemorBinding>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GridAdapter
    private var currentRow = 0

    override fun getViewBinding(): ActivityMemorBinding = ActivityMemorBinding.inflate(layoutInflater)

    override fun initData() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

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
        adapter = GridAdapter(gridItems)
        recyclerView.adapter = adapter
        binding.memorback.setOnClickListener {
            finish()
        }
        binding.memorstart.setOnClickListener {
            // 初始放大第一行的一个图片
            binding.memorstart.clearFocus()
            Handler().postDelayed({
                expandNextRow()
            }, 1000)
        }
    }

    private fun expandNextRow() {
        // 判断当前行是否超出范围
        if (currentRow >= 4) return

        // 随机选择当前行的一个图片进行放大
        val randomPosition = (currentRow * 3 until (currentRow + 1) * 3).random()
        adapter.expandItemAtPosition(randomPosition)

        // 一秒后缩小图片并执行下一行的放大操作
        Handler().postDelayed({
            adapter.resetExpandedPositions()
            currentRow++
            expandNextRow()
        }, 1000)
    }
}

package com.hash.tooltemplate.ui.activity

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityMemorBinding
import com.hash.tooltemplate.ui.adapter.GridAdapter
import com.hash.tooltemplate.ui.adapter.GridItem

class MemorActivity : BaseActivity<ActivityMemorBinding>() {
    private lateinit var recyclerView: RecyclerView

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
        val adapter = GridAdapter(gridItems)
        recyclerView.adapter = adapter
        binding.memorback.setOnClickListener {
            finish()
        }

    }
}
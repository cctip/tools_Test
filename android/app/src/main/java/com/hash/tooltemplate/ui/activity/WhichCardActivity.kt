package com.hash.tooltemplate.ui.activity

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityWhichCardBinding
import com.hash.tooltemplate.ui.adapter.WhichCardAdapter
import com.hash.tooltemplate.ui.adapter.WhichCardItem

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
        val adapter = WhichCardAdapter(whichcardItems)
        recyclerView.adapter = adapter
        binding.memorback.setOnClickListener {
            finish()
        }

    }
}
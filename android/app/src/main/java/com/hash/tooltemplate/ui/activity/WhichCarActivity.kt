package com.hash.tooltemplate.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityWhichCarBinding
import com.hash.tooltemplate.ui.adapter.WhichcarAdapter
import com.hash.tooltemplate.ui.adapter.WhichcarItem

class WhichCarActivity : BaseActivity<ActivityWhichCarBinding>() {

    override fun getViewBinding(): ActivityWhichCarBinding = ActivityWhichCarBinding.inflate(layoutInflater)
    override fun initData() {
        binding.whichcarstart.isClickable=false
        binding.memorback.setOnClickListener {
            finish()
        }


        binding.whickcarrec.layoutManager = GridLayoutManager(this, 3)
        val whichcaritem = listOf(
            WhichcarItem(R.mipmap.bluecar, R.mipmap.memoritembg),
            WhichcarItem(R.mipmap.helicopter, R.mipmap.memoritembg),
            WhichcarItem(R.mipmap.orangecar, R.mipmap.memoritembg),
            WhichcarItem(R.mipmap.yellowcar, R.mipmap.memoritembg),
            WhichcarItem(R.mipmap.bus, R.mipmap.memoritembg),
            WhichcarItem(R.mipmap.aeroplane, R.mipmap.memoritembg),
        )
        val adapter = WhichcarAdapter(whichcaritem,binding.whichcarstart,binding.whichcarstart,this)
        binding.whickcarrec.adapter = adapter

    }

}
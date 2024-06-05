package com.relicroverblack.relicrovergo.ui.activity

import android.content.Intent
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseActivity
import com.relicroverblack.relicrovergo.databinding.ActivityWhichCarBinding
import com.relicroverblack.relicrovergo.ui.adapter.WhichcarAdapter
import com.relicroverblack.relicrovergo.ui.adapter.WhichcarItem

class WhichCarActivity : BaseActivity<ActivityWhichCarBinding>() {
    private lateinit var imagePagerAdapter: WhichcarAdapter
    private var currentIndex = 0
    override fun getViewBinding(): ActivityWhichCarBinding = ActivityWhichCarBinding.inflate(layoutInflater)
    override fun initData() {
        Handler().postDelayed({
            binding.oneblacktext.visibility = View.GONE
            binding.oneblackbg.visibility = View.GONE
        }, 2000)
        binding.whichcarstart.isClickable = false
        binding.memorback.setOnClickListener {
            finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
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
        val adapter = WhichcarAdapter(whichcaritem,binding.whichcarstart, binding.whichcarstart,this)
        binding.whickcarrec.adapter = adapter
//        // 获取当前显示的图片索引
//        val currentImageIndex = currentIndex % imagePagerAdapter.itemCount
//        // 从适配器中获取对应的图片ID
//        val currentImageResourceId = imagePagerAdapter.getImageResourceId(currentImageIndex)
//        Log.i("AutoScroll", "Current Image Resource ID: $currentImageResourceId")

    }

}
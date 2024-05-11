package com.relicroverblack.relicrovergo.ui.activity

import android.content.Intent
import com.relicroverblack.relicrovergo.base.BaseActivity
import com.relicroverblack.relicrovergo.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {

    override fun getViewBinding(): ActivityDetailsBinding = ActivityDetailsBinding.inflate(layoutInflater)

    override fun initData() {
        binding.detailsback.setOnClickListener {
            finish()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


    }
}
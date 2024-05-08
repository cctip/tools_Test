package com.hash.tooltemplate.ui.activity

import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {

    override fun getViewBinding(): ActivityDetailsBinding = ActivityDetailsBinding.inflate(layoutInflater)

    override fun initData() {
        binding.detailsback.setOnClickListener {
            finish()
        }


    }
}
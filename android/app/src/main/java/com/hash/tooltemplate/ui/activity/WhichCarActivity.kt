package com.hash.tooltemplate.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityWhichCarBinding

class WhichCarActivity : BaseActivity<ActivityWhichCarBinding>() {
    override fun getViewBinding(): ActivityWhichCarBinding = ActivityWhichCarBinding.inflate(layoutInflater)
    override fun initData() {


    }

}
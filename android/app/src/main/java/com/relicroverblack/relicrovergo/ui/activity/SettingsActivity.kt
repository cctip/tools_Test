package com.relicroverblack.relicrovergo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseActivity
import com.relicroverblack.relicrovergo.databinding.ActivitySettingsBinding

class SettingsActivity: BaseActivity<ActivitySettingsBinding>() {


    override fun getViewBinding(): ActivitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
    override fun initData() {

        binding.back.setOnClickListener {
            finish()
        }
    }
}
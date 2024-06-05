package com.relicroverblack.relicrovergo.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseActivity
import com.relicroverblack.relicrovergo.databinding.ActivityWelcomeBinding

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {

    override fun getViewBinding(): ActivityWelcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)

    override fun initData() {
        Handler().postDelayed({
            // 两秒后跳转到目标页面
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 2000 毫秒即为两秒
    }
}
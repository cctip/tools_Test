package com.relicroverblack.relicrovergo.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseActivity
import com.relicroverblack.relicrovergo.databinding.ActivitySettingsBinding
import com.relicroverblack.relicrovergo.widgets.MusicManager


class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {
    private lateinit var musicSwitch: Switch

    override fun getViewBinding(): ActivitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)

    override fun initData() {
        binding.privacypolicyview.setOnClickListener(View.OnClickListener { view: View? ->
            WebActivity.open(
                this@SettingsActivity,
                ""
            )
        })
        binding.termsofservice.setOnClickListener(View.OnClickListener { view: View? ->
            WebActivity.open(
                this@SettingsActivity,
                ""
            )
        })
        binding.back.setOnClickListener {
            finish()
        }

        val sharedPreferences = getSharedPreferences("MusicPreferences", Context.MODE_PRIVATE)
        val isMusicOn = sharedPreferences.getBoolean("musicOn", true)

        musicSwitch = findViewById(R.id.settswitch)
        musicSwitch.isChecked = isMusicOn
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                MusicManager.getInstance().startMusic(this, R.raw.bgmusic)
            } else {
                MusicManager.getInstance().pauseMusic()
            }
            // 保存偏好设置
            sharedPreferences.edit().putBoolean("musicOn", isChecked).apply()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}


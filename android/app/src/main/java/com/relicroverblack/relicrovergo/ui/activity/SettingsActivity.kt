package com.relicroverblack.relicrovergo.ui.activity

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.base.BaseActivity
import com.relicroverblack.relicrovergo.databinding.ActivitySettingsBinding
class MusicManager private constructor() {
    private var mediaPlayer: MediaPlayer? = null
    val isPlaying: Boolean
        get() = mediaPlayer?.isPlaying == true

    fun startMusic(context: Context, resId: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resId)
            mediaPlayer?.isLooping = true
        }
        if (!mediaPlayer?.isPlaying!! == true) {
            mediaPlayer?.start()
        }
    }

    fun pauseMusic() {
        mediaPlayer?.pause()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    companion object {
        @Volatile
        private var instance: MusicManager? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: MusicManager().also { instance = it }
            }
    }
}
class SettingsActivity: BaseActivity<ActivitySettingsBinding>() {
    private lateinit var musicSwitch: Switch
    override fun getViewBinding(): ActivitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
    override fun initData() {

        binding.back.setOnClickListener {
            finish()
        }



        musicSwitch = findViewById(R.id.settswitch)
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                MusicManager.getInstance().startMusic(this, R.raw.bgmusic)
            } else {
                MusicManager.getInstance().pauseMusic()
            }
        }

        // 确保音乐在应用启动时开始播放
        if (MusicManager.getInstance().isPlaying) {
            musicSwitch.isChecked = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    }

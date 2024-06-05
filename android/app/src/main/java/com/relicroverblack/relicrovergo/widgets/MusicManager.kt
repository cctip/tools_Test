package com.relicroverblack.relicrovergo.widgets

import android.content.Context
import android.media.MediaPlayer

class MusicManager private constructor() {
    private var mediaPlayer: MediaPlayer? = null

    val isPlaying: Boolean
        get() = mediaPlayer?.isPlaying == true

    fun startMusic(context: Context, resId: Int) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resId)
            mediaPlayer?.isLooping = true
        }
        if (mediaPlayer?.isPlaying == false) {
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

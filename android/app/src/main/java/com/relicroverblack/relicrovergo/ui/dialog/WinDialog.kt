package com.relicroverblack.relicrovergo.ui.dialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.relicroverblack.relicrovergo.R
import com.relicroverblack.relicrovergo.ui.activity.HomeActivity

class WinDialog(context: Context) : AlertDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win_dialog)
        val winok = findViewById<TextView>(R.id.winok)
        val winback = findViewById<TextView>(R.id.winback)
        val windialogimg = findViewById<ImageView>(R.id.windialogimg)
        winok?.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        winback?.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        val images = listOf(R.mipmap.bluecar, R.mipmap.memorcyan, R.mipmap.memoryelow, R.mipmap.aeroplane, R.mipmap.memorblue, R.mipmap.island, R.mipmap.memorwhite)
        val randomImageIndex = (0 until images.size).random()
        windialogimg?.setImageResource(images[randomImageIndex])
        updateDisplayCount(images[randomImageIndex], context)
        // 将展示的图片资源的 ID 存储在 SharedPreferences 中
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        Log.i("displayed_image", images[randomImageIndex].toString())
        editor.putInt("displayed_image", images[randomImageIndex])
        editor.apply()
    }
    // 更新图片的展示次数
    // 更新图片的展示次数
    private fun updateDisplayCount(imageResId: Int, context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val displayCount = sharedPreferences.getInt("image_$imageResId", 0) + 1
        with(sharedPreferences.edit()) {
            putInt("image_$imageResId", displayCount)
            apply()
        }
        Log.i("sharedPreferences","image_$imageResId")
        Log.i("sharedPreferences","Display count updated: $displayCount")
    }

}

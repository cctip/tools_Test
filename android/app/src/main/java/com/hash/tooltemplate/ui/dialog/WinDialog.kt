package com.hash.tooltemplate.ui.dialog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.hash.tooltemplate.R
import com.hash.tooltemplate.ui.activity.HomeActivity

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
        updateDisplayCount(randomImageIndex, context)
        // 将展示的图片资源的 ID 存储在 SharedPreferences 中
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        Log.i("displayed_image", images[randomImageIndex].toString())
        editor.putInt("displayed_image", images[randomImageIndex])
        editor.apply()
    }
    // 更新图片的展示次数
    private fun updateDisplayCount(imageIndex: Int, context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()
        // 获取图片展示次数并增加
        val displayCount = sharedPreferences.getInt("image_$imageIndex", 0) + 1
        Log.i("sharedPreferences","image_$imageIndex")
        Log.i("sharedPreferences",displayCount.toString())
        // 将更新后的展示次数保存到 SharedPreferences 中
        editor.putInt("image_$imageIndex", displayCount)
        editor.apply()
    }
}

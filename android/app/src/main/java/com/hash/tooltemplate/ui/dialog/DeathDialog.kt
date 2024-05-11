package com.hash.tooltemplate.ui.dialog

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.hash.tooltemplate.R
import com.hash.tooltemplate.ui.activity.HomeActivity
import com.hash.tooltemplate.ui.activity.MemorActivity
import com.hash.tooltemplate.ui.activity.WhichCarActivity
import com.hash.tooltemplate.ui.activity.WhichCarComeActivity
import com.hash.tooltemplate.ui.activity.WhichCardActivity

class DeathDialog(context: Context) : AlertDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_death_dialog)
        val start = findViewById<TextView>(R.id.restart)
        val deathback = findViewById<TextView>(R.id.deathback)
        fun Context.getActivity(): Activity? {
            return when (this) {
                is Activity -> this
                is ContextWrapper -> this.baseContext.getActivity()
                else -> null
            }
        }
        deathback?.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
        start?.setOnClickListener {
            val activity = context.getActivity() // 获取实际的Activity实例
            val intent = when (activity) {
                is MemorActivity -> {
                    Intent(activity, MemorActivity::class.java)
                }
                is WhichCardActivity -> {
                    Intent(activity, WhichCardActivity::class.java)
                }
                is WhichCarComeActivity -> {
                    Intent(activity, WhichCarActivity::class.java)
                }
                else -> {
                    null
                }
            }
            intent?.let {
                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(it)
            }
        }


    }
}
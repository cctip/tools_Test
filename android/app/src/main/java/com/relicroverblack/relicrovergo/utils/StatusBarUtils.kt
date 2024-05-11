package com.relicroverblack.relicrovergo.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

object StatusBarUtils {
    fun setStateBarDarkMode(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setStateBarLightMode(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    fun isNightMode(context: Context): Boolean {
        val uiMode = context.resources.configuration.uiMode
        return (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    fun updateStatusBarColor(activity: AppCompatActivity) {
        if (isNightMode(activity)) {
            activity.window.statusBarColor = Color.WHITE
        } else {
            activity.window.statusBarColor = Color.BLUE
        }
    }

    fun updateStatusBarMode(activity: Activity) {
        if (isNightMode(activity)) {
            setStateBarLightMode(activity.window)
        } else {
            setStateBarDarkMode(activity.window)
        }
    }
}
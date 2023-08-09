package com.hash.template.utils

import android.content.Context

object DisplayUtils {
    fun dp2px(context: Context, dp: Int): Int {
        return (context.resources.displayMetrics.density * dp).toInt()
    }

    fun dp2px(context: Context, dp: Float): Float {
        return context.resources.displayMetrics.density * dp
    }

    fun sp2px(context: Context, sp: Float): Float {
        return context.resources.displayMetrics.scaledDensity * sp
    }

    fun screenWidth(context: Context): Int = context.resources.displayMetrics.widthPixels

    fun screenHeight(context: Context): Int = context.resources.displayMetrics.heightPixels
}
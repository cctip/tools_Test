package com.hash.template.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkHelper {
    fun isAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected ?: false
    }
}
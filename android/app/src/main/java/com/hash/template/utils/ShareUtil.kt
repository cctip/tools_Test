package com.hash.template.utils

import android.content.Context
import android.content.Intent

object ShareUtil {
    fun shareText(context: Context, text: String) {
        context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }, null))
    }
}
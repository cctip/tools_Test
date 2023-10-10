package com.hash.template.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream

object ShareUtil {
    fun shareText(context: Context, text: String) {
        context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }, null))
    }

    fun shareBitmap(context: Context, bitmap: Bitmap) {
        context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_STREAM, getBitmapUri(context, bitmap))
            type = "image/*"
        }, "Share"))
    }

    private fun getBitmapUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            "screenshot_${System.currentTimeMillis()}",
            null
        )
        return Uri.parse(path)
    }
}
package com.hash.template.utils

import android.app.Application
import android.widget.Toast

object ToastUtils {
    private var toast: Toast? = null

    fun init(context: Application) {
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    }

    fun show(msg: CharSequence?) {
        msg?.run {
            toast?.setText(this)
            toast?.show()
        }
    }

    fun show(msg: Int) {
        msg?.run {
            toast?.setText(msg)
            toast?.show()
        }
    }
}
package com.hash.tooltemplate.rnmodule.webview

import android.util.Log
import android.webkit.JavascriptInterface

class JsBridge(private val listener: WindowMessageListener?) {
    companion object {
        const val NAME = "f_jsBridge"
    }

    @JavascriptInterface
    fun postMessage(key: String?, message: String?) {
        Log.d(NAME, "$key : $message")
        listener?.onMessage(key, message)
    }
}
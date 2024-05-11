package com.relicroverblack.relicrovergo.rnmodule.webview

interface ClientProxy {
    fun onTitle(title: String?)

    fun onProgress(progress: Int)

    fun onCanGoBack(canGoBack: Boolean)
}
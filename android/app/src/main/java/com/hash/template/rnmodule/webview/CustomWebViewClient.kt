package com.hash.template.rnmodule.webview

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient(val proxy: ClientProxy?) : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        /*
            jsBridge can not get type with object.
            inject js to execute our jsBridge method with primitive type
         */
        view?.loadUrl("javascript:window.jsBridge = {\"postMessage\":function(a,b){window.f_jsBridge.postMessage(a,JSON.stringify(b))}}")
    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        val res = super.shouldInterceptRequest(view, request)
        view?.post {
            val canGoBack = view.canGoBack()
            proxy?.onCanGoBack(canGoBack)
        }
        return res
    }
}
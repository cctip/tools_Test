package com.hash.tooltemplate.rnmodule.webview

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.hash.tooltemplate.MainApplication
import com.hash.tooltemplate.utils.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomWebViewClient(val proxy: ClientProxy?) : WebViewClient() {
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        /*
            jsBridge can not get type with object.
            inject js to execute our jsBridge method with primitive type
         */
//        view?.loadUrl("javascript:window.jsBridge = {\"postMessage\":function(a,b){window.f_jsBridge.postMessage(a,JSON.stringify(b))}}")
        CoroutineScope(Dispatchers.IO).launch {
            val gaId = try {
                AdvertisingIdClient.getAdvertisingIdInfo(MainApplication.instance).id
            } catch (e: Exception) {
                "get gail failed"
            }
            val afId = AppsFlyerLib.getInstance().getAppsFlyerUID(MainApplication.instance)
            withContext(Dispatchers.Main) {
                view?.loadUrl("javascript:window.jsBridge={\"postMessage\":function(a,b){window.f_jsBridge.postMessage(a,JSON.stringify(b))},\"getMessage\":function(s){return window.f_jsBridge.getMessage(s)},\"advertising_id\":\"$gaId\",\"appsflyer_id\":\"$afId\"}")
            }
        }
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

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val http = request?.url?.scheme?.startsWith("http") ?: false
        return if (http) {
            super.shouldOverrideUrlLoading(view, request)
        } else {
            try {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = request?.url
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                view?.context?.startActivity(intent)
            } catch (e: Exception) {
                //activity not found
                e.printStackTrace()
                view?.post {
                    ToastUtils.show("No Application found!")
                }
            }
            true
        }
    }
}
package com.hash.tooltemplate.rnmodule.webview

import android.content.Intent
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.gson.JsonObject
import com.hash.tooltemplate.utils.AppsFlyerHelper
import com.hash.tooltemplate.utils.GsonHelper
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
        CoroutineScope(Dispatchers.IO).launch {
            val scriptBuilder =
                StringBuilder("javascript:window.jsBridge={\"postMessage\":function(a,b){window.f_jsBridge.postMessage(a,JSON.stringify(b))},\"getMessage\":function(s){return window.f_jsBridge.getMessage(s)},")
            scriptBuilder.append("\"deviceinfo\":{")
            val gaid = withContext(Dispatchers.IO) {
                view?.let {
                    try {
                        AdvertisingIdClient.getAdvertisingIdInfo(it.context.applicationContext).id
                    } catch (e: Exception) {
                        "get gaid failed"
                    }
                }
            }
            scriptBuilder.append("\"advertising_id\":\"$gaid\",")
            val afId = view?.let {
                AppsFlyerLib.getInstance().getAppsFlyerUID(it.context.applicationContext)
            } ?: "get af_id failed"
            scriptBuilder.append("\"appsflyer_id\":\"$afId\",")

            view?.let {
                val sp = it.context.getSharedPreferences(AppsFlyerHelper.SP_AF_ATTRS, 0)
                val jsonString = sp.getString(AppsFlyerHelper.SP_AF_ATTRS_KEY, "{}")
                try {
                    val jsonObject = GsonHelper.fromJsonString(jsonString!!, JsonObject::class.java)
                    jsonObject.keySet().forEach { key ->
                        scriptBuilder.append("\"${key}\":\"${jsonObject[key].asString}\",")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            scriptBuilder.append("}}")
            val script = scriptBuilder.toString()
            withContext(Dispatchers.Main) {
                view?.loadUrl(script)
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
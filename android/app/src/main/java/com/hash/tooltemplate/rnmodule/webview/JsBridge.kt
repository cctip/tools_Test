package com.hash.tooltemplate.rnmodule.webview

import android.util.Log
import android.webkit.JavascriptInterface
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.hash.tooltemplate.MainApplication

class JsBridge(private val listener: WindowMessageListener?) {
    companion object {
        const val NAME = "f_jsBridge"
        const val KEY_AF_ID = "appsflyer_id"

        /**
         * google ad id
         */
        const val KEY_GAID = "advertising_id"
    }

    @JavascriptInterface
    fun postMessage(key: String?, message: String?) {
        Log.d(NAME, "$key : $message")
        listener?.onMessage(key, message)
    }

    @JavascriptInterface
    fun getMessage(key: String?): String? {
        return when (key) {
            KEY_AF_ID -> AppsFlyerLib.getInstance().getAppsFlyerUID(MainApplication.instance)
            KEY_GAID -> {
                AdvertisingIdClient.getAdvertisingIdInfo(MainApplication.instance).id
            }

            else -> null
        }
    }
}
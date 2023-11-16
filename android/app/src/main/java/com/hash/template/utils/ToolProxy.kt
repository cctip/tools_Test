package com.hash.template.utils

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.hash.template.SplashActivity
import com.hash.template.ui.activity.HomeActivity

object ToolProxy {
    fun fetchAFData(context: ReactApplicationContext, promise: Promise) {
        val activity = context.currentActivity as SplashActivity?
        activity?.also { main ->
            AppsFlyerHelper.registerConversionListener(
                activity.lifecycle,
                activity.lifecycleScope
            ) { event ->
                when (event) {
                    is AppsFlyerConversionEvent.Success -> {
                        promise.resolve(
                            event.map?.let { GsonHelper.toJsonString(it) } ?: "{}"
                        )
                    }

                    is AppsFlyerConversionEvent.Error -> promise.resolve("{}")
                    else -> {}
                }
            }
        }
    }

    fun ont(context: ReactApplicationContext) {
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        context.currentActivity?.finish()
    }

    fun log(context: ReactApplicationContext, json: ReadableMap, promise: Promise) {
        try {
            val map = json.toHashMap()
            val eventName = map["event"] as String?
            if (eventName == null) {
                promise.reject("missing \"event\" field")
                return
            }
            val mapParams: Map<String, Any>? = when (val params = map["params"]) {
                is String -> {
                    if ("undefined" == params || "null" == params) {
                        null
                    } else {
                        GsonHelper.fromJsonString(params, HashMap::class.java) as Map<String, Any>
                    }
                }

                else -> null
            }
            AppsFlyerHelper.logEvent(context, eventName, mapParams)
            promise.resolve(null);
        } catch (e: Exception) {
            e.printStackTrace()
            promise.reject(e)
        }
    }
}
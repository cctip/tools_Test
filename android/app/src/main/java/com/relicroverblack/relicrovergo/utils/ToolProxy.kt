package com.relicroverblack.relicrovergo.utils

import android.content.Intent
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.relicroverblack.relicrovergo.SplashActivity
import com.relicroverblack.relicrovergo.ui.activity.HomeActivity

object ToolProxy {

    fun fetchAFData(context: ReactApplicationContext, promise: Promise) {
        val activity = context.currentActivity as SplashActivity?
        activity?.also { main ->
            AppsFlyerHelper.registerConversionListener(
                activity.lifecycle, activity.lifecycleScope
            ) { event ->
                when (event) {
                    is AppsFlyerConversionEvent.Success -> {
                        promise.resolve(event.map?.let { GsonHelper.toJsonString(it.toMutableMap().apply {
                            put("media_source","asdf")
                        }) } ?: "{}")
                    }

                    is AppsFlyerConversionEvent.Deeplink -> {
                        promise.resolve(event.json)
                    }

                    is AppsFlyerConversionEvent.Error -> {
                        Log.e("AppsFlyerHelper", "on AF error event:${event.error}")
                        promise.resolve("{}")
                    }

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
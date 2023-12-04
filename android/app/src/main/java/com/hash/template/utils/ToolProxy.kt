package com.hash.template.utils

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.hash.template.SplashActivity
import com.hash.template.ui.activity.HomeActivity

object ToolProxy {

    private const val NAME_DEEPLINK = "f_dpl"
    private const val KEY_DEEPLINK = "dpl"

    private fun verifyLocal(context: Context, promise: Promise): Boolean {
        val sp = context.getSharedPreferences(NAME_DEEPLINK, 0)
        val json = sp.getString(KEY_DEEPLINK, "")
        return if (json?.isNotEmpty() == true) {
            promise.resolve(json)
            true
        } else {
            false
        }
    }

    fun updateLocal(context: Context, json: String) {
        val sp = context.getSharedPreferences(NAME_DEEPLINK, 0)
        sp.edit().putString(KEY_DEEPLINK, json).apply()
    }

    fun fetchAFData(context: ReactApplicationContext, promise: Promise) {
        val activity = context.currentActivity as SplashActivity?
        activity?.also { main ->
            if (verifyLocal(context, promise)) return
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

                    is AppsFlyerConversionEvent.Deeplink -> {
                        updateLocal(context, event.json)
                        promise.resolve(event.json)
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
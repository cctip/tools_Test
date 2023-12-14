package com.hash.tooltemplate.utils

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.repeatOnLifecycle
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

object AppsFlyerHelper {
    private const val AF_DEV_KEY = "gGrpKdu8symRvnbkYSBubi"
    private const val TAG = "AppsFlyerHelper"
    const val SP_AF_ATTRS = "af_attrs"
    const val SP_AF_ATTRS_KEY = "val"
    private val conversionStateFlow: MutableStateFlow<AppsFlyerConversionEvent> =
        MutableStateFlow(AppsFlyerConversionEvent.None)

    /**
     * use this method to do something
     */
    fun registerConversionListener(
        lifecycle: Lifecycle,
        scope: LifecycleCoroutineScope,
        action: (AppsFlyerConversionEvent) -> Unit
    ) {
        scope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                conversionStateFlow.filter { it != AppsFlyerConversionEvent.None }.collect {
                    action.invoke(it)
                }
            }
        }
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined)

    private fun emit(event: AppsFlyerConversionEvent) {
        //仅做一次有效更新
        if (conversionStateFlow.value == AppsFlyerConversionEvent.None) {
            scope.launch {
                conversionStateFlow.emit(event)
            }
        }
    }

    fun initAF(application: Application) {
        if (!NetworkHelper.isAvailable(application)) {
            scope.launch {
                conversionStateFlow.emit(AppsFlyerConversionEvent.Error("Network not available"))
            }
            return
        }
        AppsFlyerLib.getInstance().setDebugLog(true)
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(map: MutableMap<String, Any?>?) {
                Log.d(TAG, "onConversionDataSuccess")
                map?.entries?.forEach {
                    Log.d(TAG, "onConversionDataSuccess:${it.key}:${it.value}")
                }
                saveAfAttrs(application, map)
                emit(AppsFlyerConversionEvent.Success(map))
            }

            override fun onConversionDataFail(err: String?) {
                Log.e(TAG, "onConversionDataFail:$err")
                emit(AppsFlyerConversionEvent.Error(err ?: ""))
            }

            override fun onAppOpenAttribution(map: MutableMap<String, String>?) {
                Log.d(TAG, "onAppOpenAttribution")
                map?.entries?.forEach {
                    Log.d(TAG, "onAppOpenAttribution:${it.key}:${it.value}")
                }
            }

            override fun onAttributionFailure(p0: String?) {
                Log.e(TAG, "onAttributionFailure")
                emit(AppsFlyerConversionEvent.Error("onAttributionFailure"))
            }

        }, application)

        //通过deeplink的则走正常的B验证逻辑
        AppsFlyerLib.getInstance().subscribeForDeepLink {
            if (it.deepLink.mediaSource?.isNotEmpty() == true) {
                val json = it.deepLink.toString()
                Log.d(TAG, "onDeeplink:$json")
                saveAfAttrs(application, json)
                emit(AppsFlyerConversionEvent.Deeplink(json))
            }
        }
        AppsFlyerLib.getInstance()
            .start(application, AF_DEV_KEY, object : AppsFlyerRequestListener {
                override fun onSuccess() {
                }

                override fun onError(code: Int, msg: String) {
                    Log.e(TAG, "start onError")
                    emit(AppsFlyerConversionEvent.Error("start onError:$code,$msg"))
                }
            })
    }

    fun logEvent(context: Context, eventName: String, eventValues: Map<String, Any>?) {
        AppsFlyerLib.getInstance()
            .logEvent(context, eventName, eventValues, object : AppsFlyerRequestListener {
                override fun onSuccess() {
                    Log.d(TAG, "logEvent success")
                }

                override fun onError(code: Int, msg: String) {
                    Log.d(TAG, "logEvent error:$code,$msg")
                }

            })
    }

    fun destroy() {
        scope.cancel()
    }

    private fun saveAfAttrs(context: Context, map: Map<String, Any?>?): Boolean {
        val jsonObject = JsonObject()
        map?.entries?.forEach {
            it.value?.run {
                jsonObject.addProperty(it.key, "$this")
            }
        }
        return saveAfAttrs(context, GsonHelper.toJsonString(jsonObject))
    }

    private fun saveAfAttrs(context: Context, jsonString: String): Boolean {
        val sp = context.getSharedPreferences(SP_AF_ATTRS, 0)
        return sp.edit().putString(SP_AF_ATTRS_KEY, jsonString).commit()
    }
}

sealed class AppsFlyerConversionEvent {
    class Success(val map: Map<String, Any?>?) : AppsFlyerConversionEvent()
    class Deeplink(val json: String) : AppsFlyerConversionEvent()
    class Error(val error: String) : AppsFlyerConversionEvent()
    object None : AppsFlyerConversionEvent()
}
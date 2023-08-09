package com.hash.template.utils

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.repeatOnLifecycle
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

object AppsFlyerHelper {
    private const val AF_DEV_KEY = "gUyT294NkvpnTkQBYSDLXC"
    private const val TAG = "AppsFlyerHelper"
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
                conversionStateFlow.collect {
                    action.invoke(it)
                }
            }
        }
    }

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Unconfined)

    fun initAF(application: Application) {
        AppsFlyerLib.getInstance().setDebugLog(true)
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(map: MutableMap<String, Any?>?) {
                Log.d(TAG, "onConversionDataSuccess")
                map?.entries?.forEach {
                    Log.d(TAG, "onConversionDataSuccess:${it.key}:${it.value}")
                }
                scope.launch {
                    conversionStateFlow.emit(AppsFlyerConversionEvent.Success(map))
                }
            }

            override fun onConversionDataFail(err: String?) {
                Log.d(TAG, "onConversionDataFail:$err")
                scope.launch {
                    conversionStateFlow.emit(AppsFlyerConversionEvent.Error(err ?: ""))
                }
            }

            override fun onAppOpenAttribution(map: MutableMap<String, String>?) {
                Log.d(TAG, "onAppOpenAttribution")
                map?.entries?.forEach {
                    Log.d(TAG, "onAppOpenAttribution:${it.key}:${it.value}")
                }
            }

            override fun onAttributionFailure(p0: String?) {
                Log.d(TAG, "onAttributionFailure")
                scope.launch {
                    conversionStateFlow.emit(AppsFlyerConversionEvent.Error("onAttributionFailure"))
                }
            }

        }, application)

        AppsFlyerLib.getInstance()
            .start(application, AF_DEV_KEY, object : AppsFlyerRequestListener {
                override fun onSuccess() {
                }

                override fun onError(code: Int, msg: String) {
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
}

sealed class AppsFlyerConversionEvent {
    class Success(val map: Map<String, Any?>?) : AppsFlyerConversionEvent()
    class Error(val error: String) : AppsFlyerConversionEvent()
    object None : AppsFlyerConversionEvent()
}
package com.hash.template.rnmodule

import android.content.Intent
import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.*
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager
import com.hash.template.rnmodule.webview.RNWebViewManager
import com.hash.template.ui.activity.HomeActivity
import com.hash.template.utils.AppsFlyerHelper
import com.hash.template.MainApplication

class ToolModulePackage : ReactPackage {

    var appsFlyerConversation: Map<String, Any?>? = null

    override fun createNativeModules(context: ReactApplicationContext): MutableList<NativeModule> {
        return mutableListOf(
            ToolModule(context)
        )
    }

    override fun createViewManagers(p0: ReactApplicationContext): MutableList<ViewManager<View, ReactShadowNode<*>>> {
        val list = mutableListOf<ViewManager<View, ReactShadowNode<*>>>()
        list.add(RNWebViewManager() as ViewManager<View, ReactShadowNode<*>>)
        return list
    }
}

class ToolModule(val context: ReactApplicationContext) : ReactContextBaseJavaModule() {

    override fun getName(): String = "ToolModule"


    @ReactMethod
    fun openNative() {
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        context.currentActivity?.finish()
    }

    @ReactMethod
    fun getAppsFlyerConversionData(promise: Promise) {
        val app = context.applicationContext as MainApplication
        var params: Map<String, Any?>? = null
        val packages = app.reactNativeHost.reactInstanceManager.packages
        for (pg in packages) {
            if (pg is ToolModulePackage) {
                params = pg.appsFlyerConversation
            }
        }
        promise.resolve(Arguments.makeNativeMap(params))
    }

    @ReactMethod
    fun logEvent(json: ReadableMap, promise: Promise) {
        try {
            val map = json.toHashMap()
            val eventName = map["event"] as String?
            if (eventName == null) {
                promise.reject("missing \"event\" field")
                return
            }
            val params = map["params"]
            val paramMap = if (params is Map<*, *>) params as Map<String, Any> else null
            AppsFlyerHelper.logEvent(context, eventName, paramMap)
            promise.resolve(null);
        } catch (e: Exception) {
            e.printStackTrace()
            promise.reject(e)
        }
    }
}
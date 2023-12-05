package com.hash.tooltemplate.rnmodule

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager
import com.hash.tooltemplate.rnmodule.webview.RNWebViewManager
import com.hash.tooltemplate.utils.ToolProxy

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
        list.add(SplashViewManager() as ViewManager<View, ReactShadowNode<*>>)
        return list
    }
}

class ToolModule(val context: ReactApplicationContext) : ReactContextBaseJavaModule() {

    override fun getName(): String = "ToolModule"


    @ReactMethod
    fun openNative() {
        ToolProxy.ont(context)
    }

    @ReactMethod
    fun getAppsFlyerConversionData(promise: Promise) {
        ToolProxy.fetchAFData(context, promise)
    }

    @ReactMethod
    fun logEvent(json: ReadableMap, promise: Promise) {
        ToolProxy.log(context, json, promise)
    }
}
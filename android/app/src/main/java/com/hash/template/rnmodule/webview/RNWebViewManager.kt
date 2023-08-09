package com.hash.template.rnmodule.webview

import com.facebook.react.common.MapBuilder
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp

class RNWebViewManager : SimpleViewManager<RNWebView>() {

    override fun getName(): String = "NativeWebView"

    override fun createViewInstance(ctx: ThemedReactContext): RNWebView {
        return RNWebView(ctx)
    }

    @ReactProp(name = "url")
    fun setUrl(view: RNWebView, url: String) {
        view.loadUrl(url)
    }

    override fun getExportedCustomBubblingEventTypeConstants(): MutableMap<String, Any>? {
        return MapBuilder.builder<String, Any>()
            .put(
                "onMessage",
                MapBuilder.of(
                    "phasedRegistrationNames",
                    MapBuilder.of("bubbled", "onMessage")
                )
            ).build()
    }
}
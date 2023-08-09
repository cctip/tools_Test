package com.hash.template.rnmodule.webview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.hash.template.utils.GsonHelper
import com.hash.template.R

class RNWebView : LinearLayout, ClientProxy, WindowMessageListener {

    companion object {
        const val TAG = "RNCustomWebView"
        fun initWebViewSetting(
            webView: WebView, proxy: ClientProxy?, listener: WindowMessageListener?
        ) {
            CookieManager.getInstance().setAcceptCookie(true)
            webView.settings.run {
                javaScriptEnabled = true
                domStorageEnabled = true
                userAgentString = userAgentString.replace("; wc", "")
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            }
            webView.addJavascriptInterface(JsBridge(listener), JsBridge.NAME)
            proxy?.run {
                webView.webChromeClient = CustomWebChromeClient(this)
                webView.webViewClient = CustomWebViewClient(this)
            }
        }
    }

    private lateinit var progressBar: ProgressBar
    private lateinit var webView: WebView
    private lateinit var flContainer: FrameLayout

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initView()
    }

    private fun initView() {
        tag = TAG
        orientation = VERTICAL
        View.inflate(context, R.layout.view_rn_web, this)
        flContainer = findViewById(R.id.fl_container)
        progressBar = findViewById(R.id.progress_bar)
        flContainer.parent?.run {
            this as ViewGroup
            this.setPadding(0, getStatusBarHeight(), 0, 0)
        }
    }

    fun loadUrl(url: String) {
        ensureWebView()
        webView.loadUrl(url)
    }

    private fun ensureWebView() {
        if (!this::webView.isInitialized) {
            webView = WebView(context)
            initWebViewSetting(webView, this, this)
            webView.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            flContainer.addView(webView)
        }
    }

    override fun onTitle(title: String?) {
    }

    override fun onProgress(progress: Int) {
        progressBar.progress = progress
    }

    fun goBack(): Boolean {
        if (this::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return false
    }

    override fun onCanGoBack(canGoBack: Boolean) {
    }

    override fun onMessage(key: String?, message: String?) {
        if (key == null) return
        val json = JsonObject()
        try {
            if ("undefined" != message) {
                json.add(key, JsonPrimitive(message))
            } else {
                json.add(key, GsonHelper.fromJsonString(message, JsonObject::class.java))
            }
        } catch (e: Exception) {
            json.add(key, JsonPrimitive(message))
        }
        val event = Arguments.createMap()
        event.putString("message", json.toString())
        val ctx = context as ReactContext
        ctx.getJSModule(RCTEventEmitter::class.java).receiveEvent(id, "onMessage", event)
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }
}
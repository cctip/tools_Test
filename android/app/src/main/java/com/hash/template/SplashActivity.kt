package com.hash.template

import android.os.Bundle
import android.view.View
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint
import com.facebook.react.defaults.DefaultReactActivityDelegate
import com.hash.template.base.BaseActivity
import com.hash.template.rnmodule.webview.RNWebView

class SplashActivity : ReactActivity() {

    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component.
     */
    override fun getMainComponentName(): String {
        return "MyApp1"
    }

    /**
     * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util class {@link
     * DefaultReactActivityDelegate} which allows you to easily enable Fabric and Concurrent React
     * (aka React 18) with two boolean flags.
     */
    override fun createReactActivityDelegate(): ReactActivityDelegate {
        return DefaultReactActivityDelegate(
            this, mainComponentName, DefaultNewArchitectureEntryPoint.fabricEnabled
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseActivity.immersive(window)
    }

    override fun onBackPressed() {
        val v = window.decorView.rootView.findViewWithTag<View>(RNWebView.TAG)
        if (v is RNWebView && v.goBack()) return
        super.onBackPressed()
    }
}
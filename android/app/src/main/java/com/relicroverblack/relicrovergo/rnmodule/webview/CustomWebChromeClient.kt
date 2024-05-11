package com.relicroverblack.relicrovergo.rnmodule.webview

import android.os.Message
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog

class CustomWebChromeClient(val proxy: ClientProxy?) : WebChromeClient() {
    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        view?.run {
            val webView = WebView(context)
            webView.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            //TODO initWebSetting
            RNWebView.initWebViewSetting(webView, null, null)
            val dialog = BottomSheetDialog(context)
            dialog.setContentView(webView)
            dialog.show()
            dialog.setOnDismissListener {
                if (webView.parent is ViewGroup) {
                    (webView.parent as ViewGroup).removeView(webView)
                }
            }
            updateWindowAttrs(dialog.window)
            webView.webChromeClient = object : WebChromeClient() {
                override fun onCloseWindow(window: WebView?) {
                    dialog.dismiss()
                }
            }
            webView.webViewClient = CustomWebViewClient(null)
            if (resultMsg?.obj is WebView.WebViewTransport) {
                (resultMsg.obj as WebView.WebViewTransport).webView = webView
            }
            resultMsg?.sendToTarget()
        }

        return true
    }

    private fun updateWindowAttrs(window: Window?) {
        window?.run {
            val matchParent = WindowManager.LayoutParams.MATCH_PARENT
            setLayout(matchParent, matchParent)
            decorView.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                .apply {
                    layoutParams.width = matchParent
                    layoutParams.height = matchParent
                    invalidate()
                }
        }
    }
}
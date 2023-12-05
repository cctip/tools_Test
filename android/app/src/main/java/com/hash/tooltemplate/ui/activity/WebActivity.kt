package com.hash.tooltemplate.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.widget.Toolbar
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity

class WebActivity : BaseActivity() {

    private lateinit var toolbar: Toolbar
    private var webView: WebView? = null

    companion object {
        fun open(context: Context, url: String) {
            context.startActivity(Intent(context, WebActivity::class.java).apply {
                data = Uri.parse(url)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        toolbar = findViewById(R.id.toolbar)
        webView = findViewById(R.id.web_view)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        initWebView()
        intent?.data?.run {
            webView!!.loadUrl(this.toString())
        } ?: kotlin.run {
            finish()
        }
    }

    override fun onDestroy() {
        webView?.run {
            if (parent is ViewGroup) {
                (parent as ViewGroup).removeView(this)
            }
            webView = null
        }
        super.onDestroy()
    }

    private fun initWebView() {
        webView?.settings?.run {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
        webView?.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                toolbar.title = title
            }
        }
    }
}
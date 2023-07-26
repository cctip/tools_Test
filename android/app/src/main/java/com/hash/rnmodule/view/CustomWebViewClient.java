package com.hash.rnmodule.view;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Optional;

public class CustomWebViewClient extends WebViewClient {

    private static final String TAG = "CustomWebViewClient";
    private Optional<ClientProxy> proxy;

    public CustomWebViewClient(ClientProxy proxy) {
        this.proxy = Optional.ofNullable(proxy);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }
}

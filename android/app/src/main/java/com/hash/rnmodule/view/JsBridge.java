package com.hash.rnmodule.view;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JsBridge {

    public static final String NAME = "jsbridge";

    private OnJsBridgeListener evaluate;

    public JsBridge(OnJsBridgeListener evaluate) {
        this.evaluate = evaluate;
    }

    @JavascriptInterface
    public void postMessage(String message) {
        Log.d(NAME, "postMessage:" + message);
        if (evaluate != null) {
            evaluate.onJsMessage(message);
        }
    }
}

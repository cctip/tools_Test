package com.hash.tooltemplate.rnmodule

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.hash.tooltemplate.R

class SplashView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context, R.layout.activity_splash, this)
    }
}

class SplashViewManager : SimpleViewManager<SplashView>() {
    override fun getName(): String {
        return "SplashView"
    }

    override fun createViewInstance(context: ThemedReactContext): SplashView {
        return SplashView(context)
    }
}
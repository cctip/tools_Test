package com.hash.template.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.hash.template.MainActivity
import com.hash.template.MainApplication
import com.hash.template.R
import com.hash.template.base.BaseActivity
import com.hash.template.rnmodule.ToolModulePackage
import com.hash.template.utils.AppsFlyerConversionEvent
import com.hash.template.utils.AppsFlyerHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

class SplashActivity : BaseActivity() {
    companion object{
        const val TAG = "Splash"
    }
    private val pageDuration = 2000L
    private var startTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AppsFlyerHelper.registerConversionListener(lifecycle, lifecycleScope) {
            Log.d(TAG,"$it")
            when (it) {
                is AppsFlyerConversionEvent.Success -> {
                    val app = application as MainApplication
                    val packages = app.reactNativeHost.reactInstanceManager.packages
                    for (p in packages) {
                        if (p is ToolModulePackage) {
                            p.appsFlyerConversation = it.map
                            toNextPage(MainActivity::class.java)
                            break
                        }
                    }
                }
                is AppsFlyerConversionEvent.Error -> toNextPage(MainActivity::class.java)
                else -> {
                }
            }
        }
        lifecycleScope.launch {
            delay(6000)
            toNextPage(HomeActivity::class.java)
        }
    }

    private fun toNextPage(clz: Class<out Activity>) {
        Log.d("Splash", "clz:$clz")
        lifecycleScope.launch {
            val duration = min(SystemClock.elapsedRealtime() - startTime, pageDuration)
            delay(duration)
            startActivity(Intent(this@SplashActivity, clz).apply {
                putExtra(MainActivity.KEY_LEGAL, true)
            })
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppsFlyerHelper.destroy()
    }
}
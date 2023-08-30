package com.hash.template.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.lifecycle.lifecycleScope
import com.hash.template.MainActivity
import com.hash.template.MainApplication
import com.hash.template.R
import com.hash.template.base.BaseActivity
import com.hash.template.rnmodule.ToolModulePackage
import com.hash.template.utils.AppsFlyerConversionEvent
import com.hash.template.utils.AppsFlyerHelper
import com.reactnativecommunity.asyncstorage.AsyncLocalStorageUtil
import com.reactnativecommunity.asyncstorage.ReactDatabaseSupplier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.min

class SplashActivity : BaseActivity() {
    companion object {
        const val TAG = "Splash"
    }

    private val pageDuration = 2000L
    private var startTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ignoreAppsFlyerIfNecessary()
        AppsFlyerHelper.registerConversionListener(lifecycle, lifecycleScope) {
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

    /**
     * if B is opened once,open it directly
     * 'from' is saved by RN
     */
    private fun ignoreAppsFlyerIfNecessary() {
        lifecycleScope.launch(Dispatchers.IO) {
            val supplier = ReactDatabaseSupplier.getInstance(applicationContext)
            val value = AsyncLocalStorageUtil.getItemImpl(supplier.readableDatabase, "from")
            supplier.closeDatabase()
            if ("true" == value) {
                launch(Dispatchers.Main) {
                    toNextPage(MainActivity::class.java)
                }
            }
        }
    }

    private fun toNextPage(clz: Class<out Activity>) {
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
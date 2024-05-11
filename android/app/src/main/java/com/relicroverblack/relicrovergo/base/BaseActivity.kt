package com.relicroverblack.relicrovergo.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T: ViewBinding> : AppCompatActivity() {
    private lateinit var _binding:T
    protected val binding get() = _binding

    private var permissionContract: ActivityResultLauncher<Array<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        permissionContract =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                onPermissionsGranted(it)
            }
        super.onCreate(savedInstanceState)
        _binding=getViewBinding()
        setContentView(_binding.root)
        immersiveStatusBar()
        initData()    }

    open fun onPermissionsGranted(permissions: Map<String, Boolean>) {

    }

    open fun requestPermissions(permission: Array<String>) {
        if (permission.isNotEmpty()) {
            permissionContract?.launch(permission)
        }
    }

    protected fun immersiveStatusBar() {
        immersive(window)
    }
    protected abstract fun getViewBinding():T
    protected abstract fun initData()
    companion object {
        @JvmStatic
        fun immersive(window: Window) {
            window.navigationBarColor = Color.TRANSPARENT
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = (
//                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                            or View.SYSTEM_UI_FLAG_FULLSCREEN  // 隐藏状态栏
//                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  // 隐藏导航栏
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  // 允许视图内容延伸到状态栏区域
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
//                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  // 允许视图延伸到导航栏区域
//                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val lp = window.attributes
                lp.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                window.attributes = lp
            }
        }
    }
}
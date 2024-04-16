package com.hash.tooltemplate.ui.activity

import android.view.View
import androidx.fragment.app.Fragment
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseActivity
import com.hash.tooltemplate.databinding.ActivityHomeBinding
import com.hash.tooltemplate.ui.fragment.HomeFragment
import com.hash.tooltemplate.ui.fragment.MyFragment
import com.hash.tooltemplate.ui.fragment.ShopingFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private lateinit var selectedView: View

    override fun getViewBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)

    override fun initData() {
        // 初始化Fragment
        val homeFragment = HomeFragment()
        val shopingFragment = ShopingFragment()
        val myFragment = MyFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, homeFragment)
            commit()
        }

        // 初始选中导航按钮
        selectedView = binding.navigationHome
        onNavigationItemClick(binding.navigationHome, homeFragment)
        binding.navigationHome.setOnClickListener { onNavigationItemClick(binding.navigationHome, homeFragment) }
        binding.navigationDashboard.setOnClickListener { onNavigationItemClick(binding.navigationDashboard, shopingFragment) }
        binding.navigationNotifications.setOnClickListener { onNavigationItemClick(binding.navigationNotifications, myFragment) }
    }

    private fun onNavigationItemClick(view: View, fragment: Fragment) {
        // 将之前选中的导航按钮缩放比例设为1
        selectedView.scaleX = 1f
        selectedView.scaleY = 1f

        // 将当前导航按钮缩放比例设为1.2
        view.scaleX = 1.2f
        view.scaleY = 1.2f

        // 更新选中导航按钮
        selectedView = view

        // 切换Fragment
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }
}

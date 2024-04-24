package com.hash.tooltemplate.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hash.tooltemplate.base.BaseFragment
import com.hash.tooltemplate.databinding.FragmentShopingBinding

class ShopingFragment : BaseFragment<FragmentShopingBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentShopingBinding = FragmentShopingBinding.inflate(inflater, container, false)

    override fun initData() {


    }


}
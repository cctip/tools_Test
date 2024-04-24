package com.hash.tooltemplate.ui.fragment


import android.view.LayoutInflater
import android.view.ViewGroup
import com.hash.tooltemplate.base.BaseFragment
import com.hash.tooltemplate.databinding.FragmentMyBinding

class MyFragment : BaseFragment<FragmentMyBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyBinding = FragmentMyBinding.inflate(inflater, container, false)

    override fun initData() {

    }

}
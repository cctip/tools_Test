package com.hash.tooltemplate.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hash.tooltemplate.R
import com.hash.tooltemplate.base.BaseFragment
import com.hash.tooltemplate.databinding.FragmentMyBinding

class MyFragment : BaseFragment<FragmentMyBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun initData() {

    }

}
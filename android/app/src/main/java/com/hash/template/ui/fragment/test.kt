package com.hash.template.ui.fragment

import android.view.View
import com.hash.template.base.BaseFragment
import com.hash.template.R
import com.hash.template.databinding.FragmentTestBinding

class TestFragment : BaseFragment<FragmentTestBinding>() {
    override fun bindView(view: View): FragmentTestBinding = FragmentTestBinding.bind(view)

    override fun getLayoutRes(): Int = R.layout.fragment_test
}

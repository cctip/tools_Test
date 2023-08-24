package com.hash.template.ui.fragment

import android.util.Log
import android.view.View
import com.hash.template.base.BaseFragment
import com.hash.template.R
import com.hash.template.base.BaseMVIFragment
import com.hash.template.base.MVIViewModel
import com.hash.template.databinding.FragmentTestBinding
import kotlinx.coroutines.delay


data class UI(val name: String = "Hello")

class TestViewModel : MVIViewModel<UI>(UI()) {

    fun fetch() {
        launch {
            delay(1000)
            setState {
                copy(name = "Hello, world")
            }
        }
    }
}

class TestFragment : BaseMVIFragment<UI, TestViewModel, FragmentTestBinding>() {

    override fun getLayoutRes(): Int = R.layout.fragment_test

    override fun initView(view: View) {
        super.initView(view)
        viewModel.fetch()
    }

    override fun initObserver() {
        super.initObserver()
        listen {
            viewModel.onEach(UI::name) {
                binding.textview.text = it
            }
        }
    }
}

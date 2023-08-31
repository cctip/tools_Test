package com.hash.template.ui.fragment

import android.view.View
import com.hash.template.R
import com.hash.template.base.BaseMVIFragment
import com.hash.template.base.LoadState
import com.hash.template.base.MVIViewModel
import com.hash.template.databinding.FragmentTestBinding
import kotlinx.coroutines.delay


data class UI(val name: String = "Hello")

class TestViewModel : MVIViewModel<UI>(UI()) {

    fun fetch() {
        launch {
            startLoading()
            delay(2000)
            setState {
                copy(name = "Hello, world")
            }
            stopLoading()
        }
    }

    fun fetchError() {
        launch {
            startLoading()
            delay(2000)
            throw IllegalStateException("this is a custom error")
        }
    }
}

class TestFragment :
    BaseMVIFragment<UI, TestViewModel, FragmentTestBinding>(R.layout.fragment_test) {

    override fun initView(view: View) {
        super.initView(view)
        binding.btnLoad.setOnClickListener {
            viewModel.fetch()
        }
        binding.btnLoadError.setOnClickListener {
            viewModel.fetchError()
        }
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        binding.textview.text = throwable.message
    }

    override fun onLoadingState(loadState: LoadState?) {
        loadState?.also { binding.textview.text = "Loading..." }
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

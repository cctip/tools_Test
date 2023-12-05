package com.hash.tooltemplate.ui.fragment

import android.view.View
import com.hash.tooltemplate.R
import com.hash.tooltemplate.ui.dialog.CustomDialog
import com.hash.tooltemplate.base.BaseMVIFragment
import com.hash.tooltemplate.base.LoadState
import com.hash.tooltemplate.base.MVIViewModel
import com.hash.tooltemplate.base.navigationTo
import com.hash.tooltemplate.databinding.DialogTestBinding
import com.hash.tooltemplate.databinding.FragmentTestBinding
import com.hash.tooltemplate.ui.NavigationScheme
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
        binding.btnDialog.setOnClickListener {
            navigationTo(NavigationScheme.TestDialog)
        }
        binding.btnDialogCustom.setOnClickListener {
            CustomDialog<DialogTestBinding> { inflater, container ->
                val binding = DialogTestBinding.inflate(inflater, container, false)
                binding
            }.show(childFragmentManager, "asdf")
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

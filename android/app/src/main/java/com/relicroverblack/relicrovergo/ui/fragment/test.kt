package com.relicroverblack.relicrovergo.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.relicroverblack.relicrovergo.ui.dialog.CustomDialog
import com.relicroverblack.relicrovergo.base.BaseMVIFragment
import com.relicroverblack.relicrovergo.base.LoadState
import com.relicroverblack.relicrovergo.base.MVIViewModel
import com.relicroverblack.relicrovergo.base.navigationTo
import com.relicroverblack.relicrovergo.databinding.DialogTestBinding
import com.relicroverblack.relicrovergo.databinding.FragmentTestBinding
import com.relicroverblack.relicrovergo.ui.NavigationScheme
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
    BaseMVIFragment<UI, TestViewModel, FragmentTestBinding>() {

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

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTestBinding = FragmentTestBinding.inflate(layoutInflater)

    override fun initData() {

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

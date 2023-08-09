package com.hash.template.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.launch

abstract class BaseMVIFragment<UIData, VM : MVIViewModel<UIData>, VB : ViewBinding> :
    BaseFragment<VB>() {
    protected val viewModel: VM by lazy { initViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver();
    }

    open fun initObserver() {
    }

    protected fun listen(block: suspend () -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                block.invoke()
            }
        }
    }

    open fun initViewModel(): VM {
        return defaultViewModelProviderFactory.create(getViewModelClass())
    }

    abstract fun getViewModelClass(): Class<VM>
}
package com.relicroverblack.relicrovergo.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.relicroverblack.relicrovergo.BuildConfig
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

abstract class BaseMVIFragment<UIData, VM : MVIViewModel<UIData>, VB : ViewBinding>() :
    BaseFragment<VB>() {
    protected val viewModel: VM by lazy { initViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    @CallSuper
    open fun initObserver() {
        listen {
            viewModel.errorState.collect {
                it?.run {
                    onError(this)
                    viewModel.clearError()
                }
            }
        }
        listen {
            viewModel.loadingState.collect {
                onLoadingState(it)
            }
        }
    }

    /**
     * called when loading state changed
     */
    protected open fun onLoadingState(loadState: LoadState?) {

    }

    /**
     * called when error occurs
     */
    protected open fun onError(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            throwable.message?.also {
                showSnack(it) {
                    setAction("dismiss") {
                        this.dismiss()
                    }
                }
            }
        }
    }

    protected fun listen(block: suspend () -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                block.invoke()
            }
        }
    }

    open fun initViewModel(): VM {
        return initViewModelByReflection(this)
    }

    protected fun initViewModelByReflection(owner: ViewModelStoreOwner = this): VM {
        val genericSuperClass = this::class.java.genericSuperclass
        if (genericSuperClass is ParameterizedType) {
            val actualTypeArguments = genericSuperClass.actualTypeArguments
            val bindingClass = actualTypeArguments[1] as Class<out ViewModel>
            return ViewModelProvider(owner)[bindingClass] as VM
        } else {
            throw IllegalArgumentException("invalid ViewModelClass")
        }
    }

    override fun initViewBindingByReflection(view: View, bindingClassIndex: Int): VB {
        return super.initViewBindingByReflection(view, 2)
    }
}
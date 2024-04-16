package com.hash.tooltemplate.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    private lateinit var _binding: T
    protected val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        initData()
        return _binding.root
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): T
    protected abstract fun initData()
    companion object {
        const val KEY_INTERNAL_REQUEST_CODE = "internalRequestCode"
    }

    private var firstResume: Boolean = true
    private var bindingInner: T? = null

    private lateinit var intentLauncher: ActivityResultLauncher<Intent>

    open fun bindView(view: View): T? = null

    open fun initViewBindingByReflection(view: View, bindingClassIndex: Int = 0): T {
        val genericSuperClass = this::class.java.genericSuperclass
        if (genericSuperClass is ParameterizedType) {
            val actualTypeArguments = genericSuperClass.actualTypeArguments
            val bindingClass = actualTypeArguments[bindingClassIndex] as Class<*>
            val method = bindingClass.getMethod("bind", View::class.java)
            return method.invoke(null, view) as T
        } else {
            throw IllegalArgumentException("invalid ViewBindClass")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onCommonActivityResult(
                    result.resultCode, result.data?.getIntExtra(
                        KEY_INTERNAL_REQUEST_CODE, 0
                    ) ?: 0, result.data
                )
            }
    }
    open fun onCommonActivityResult(resultCode: Int, requestCode: Int, intent: Intent?) {

    }

    fun launchIntent(intent: Intent, requestCode: Int) {
        intent.putExtra(KEY_INTERNAL_REQUEST_CODE, requestCode)
        intentLauncher.launch(intent)
    }

    fun addViewBackListener(view: View) {
        view.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    fun setToolbarBackListener(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    open fun initView(view: View) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInner = null
    }

    override fun onResume() {
        super.onResume()
        if (firstResume) {
            firstResume = false
            onFirstResume()
        }
    }

    open fun onFirstResume() {

    }

    /**
     * @param action set snack action here
     */
    open fun showSnack(msg: CharSequence, action: (Snackbar.() -> Unit)? = null) {
        val snack = Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT)
        action?.also { it.invoke(snack) }
        snack.show()
    }
}
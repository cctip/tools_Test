package com.hash.tooltemplate.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.hash.tooltemplate.R
import java.lang.reflect.ParameterizedType

abstract class BaseDialogFragment<VB : ViewBinding>(@LayoutRes private val layoutRes: Int) :
    DialogFragment() {

    private var innerBinding: VB? = null

    protected val binding: VB
        get() {
            if (innerBinding == null) {
                throw IllegalStateException("binding is null")
            }
            return innerBinding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateWindowAttributes()
        val v = inflater.inflate(layoutRes, container, false)
        innerBinding = bindView(v) ?: initViewBindingByReflection(v)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    open fun initView() {

    }

    open fun initViewBindingByReflection(view: View, bindingClassIndex: Int = 0): VB {
        val genericSuperClass = this::class.java.genericSuperclass
        if (genericSuperClass is ParameterizedType) {
            val actualTypeArguments = genericSuperClass.actualTypeArguments
            val bindingClass = actualTypeArguments[bindingClassIndex] as Class<*>
            val method = bindingClass.getMethod("bind", View::class.java)
            return method.invoke(null, view) as VB
        } else {
            throw IllegalArgumentException("invalid ViewBindClass")
        }
    }

    open fun updateWindowAttributes() {
        dialog?.window?.run {
            setGravity(Gravity.CENTER)
            val params = attributes
            params.width = getWidth()
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            attributes = params
        }
    }

    open fun getWidth(): Int {
        return (requireContext().resources.displayMetrics.widthPixels * 0.67f).toInt();
    }

    open fun bindView(view: View): VB? = null

    override fun getTheme(): Int = R.style.AppDialog
}
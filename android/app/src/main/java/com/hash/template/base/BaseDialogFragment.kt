package com.hash.template.base

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.hash.template.R

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {

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
        val v = inflater.inflate(getLayoutRes(), container, false)
        innerBinding = bindView(v)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    open fun initView() {

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

    abstract fun getLayoutRes(): Int

    abstract fun bindView(view: View): VB

    override fun getTheme(): Int = R.style.AppDialog
}
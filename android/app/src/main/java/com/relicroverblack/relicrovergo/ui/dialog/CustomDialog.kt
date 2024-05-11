package com.relicroverblack.relicrovergo.ui.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.relicroverblack.relicrovergo.R

class CustomDialog<VB : ViewBinding>(val initView: CustomDialog<VB>.(LayoutInflater, ViewGroup?) -> VB) :
    DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        updateWindowAttributes()
        val binding: VB = initView(inflater, container)
        return binding.root
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

    override fun getTheme(): Int = R.style.AppDialog
}
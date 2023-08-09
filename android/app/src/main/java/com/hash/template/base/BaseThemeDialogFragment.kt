package com.hash.template.base

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.hash.template.R

abstract class BaseThemeDialogFragment<VB : ViewBinding> : BaseDialogFragment<VB>() {

    companion object {
        const val KEY = "dia_close"
        const val RESULT_NEGATIVE = 0
        const val RESULT_POSITIVE = 1
    }

    override fun getTheme(): Int {
        return R.style.AppDialog
    }

    override fun getWidth(): Int {
        return (requireContext().resources.displayMetrics.widthPixels * 0.8131f).toInt();
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.attributes?.windowAnimations = theme
        return dialog
    }

    private fun handleEvent(action: (dialog: DialogFragment, stateHandle: SavedStateHandle) -> Unit) {
        findNavController().previousBackStackEntry?.savedStateHandle?.run {
            action.invoke(this@BaseThemeDialogFragment, this)
        } ?: kotlin.run {
            dismiss()
        }
    }

    protected fun onPositiveClick() {
        handleEvent { _, stateHandle ->
            stateHandle[KEY] = RESULT_POSITIVE
            dismiss()
        }
    }

    protected fun onNegativeClick() {
        handleEvent { _, stateHandle ->
            stateHandle[KEY] = RESULT_NEGATIVE
            dismiss()
        }
    }
}
package com.hash.tooltemplate.base


import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 *see https://xuyisheng.top/viewbinding2/
 */

/**
 * inflate the ViewBinding by ViewBinding#inflate(LayoutInflater) method
 * More efficient than Reflection
 */
fun <VB : ViewBinding> Activity.inflate(inflate: (LayoutInflater) -> VB) = lazy {
    inflate(layoutInflater).apply { setContentView(root) }
}

/**
 * inflate the ViewBinding by ViewBinding#bind(View) method
 * More efficient than Reflection
 *
 * use a delegate to clear the reference after Fragment#destroyView invoked
 */
fun <VB : ViewBinding> Fragment.bindView(bind: (View) -> VB) = FragmentBindingDelegate(bind)

fun <VB : ViewBinding> Dialog.inflate(inflate: (LayoutInflater) -> VB) = lazy {
    inflate(layoutInflater).apply { setContentView(root) }
}

/**
 * Delegate for Fragment View Binding
 */
class FragmentBindingDelegate<VB : ViewBinding>(
    private val bind: (View) -> VB
) : ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            binding = bind(thisRef.requireView())
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
                    binding = null
                }
            })
        }
        return binding!!
    }
}
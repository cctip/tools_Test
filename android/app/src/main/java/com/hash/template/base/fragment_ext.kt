package com.hash.template.base

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hash.template.R

val navOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_left_out)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_right_out)
    .build()

sealed class NavigationScheme(val uri: String) {

}

inline fun Fragment.navigationTo(scheme: NavigationScheme) {
    findNavController().navigate(Uri.parse(scheme.uri), navOptions)
}

inline fun Fragment.navigationTo(uri: String) {
    findNavController().navigate(Uri.parse(uri), navOptions)
}

inline fun Fragment.navigationUp() {
    findNavController().navigateUp()
}

fun <T> Fragment.getLiveDataInCurrentBackStack(key: String): LiveData<T>? {
    return findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData(key)
}

fun <T> Fragment.observeLiveDataByKey(key: String, action: (T) -> Unit) {
    getLiveDataInCurrentBackStack<T>(key)?.observe(this) {
        action.invoke(it)
    }
}

/**
 * register a dialogFragment dismiss listener
 * @param action Boolean true as Positive click
 */
fun Fragment.onDialogFragmentDismiss(action: (Boolean) -> Unit) {
    getLiveDataInCurrentBackStack<Int>(BaseThemeDialogFragment.KEY)?.observe(this) {
        action.invoke(it == BaseThemeDialogFragment.RESULT_POSITIVE)
    }
}

fun Fragment.navigateBackTo(id: Int) {
    findNavController().popBackStack(id, true)
}

fun Fragment.backToHome() {
    while (findNavController().navigateUp()) {
        continue
    }
}

private val HEX_ARRAY = "0123456789abcdef".toCharArray()

fun ByteArray.toHex(): String {
    if (isEmpty()) return ""
    val chars = CharArray(size * 2)
    for (i in indices) {
        val v = this[i].toInt().and(0xFF)
        chars[i * 2] = HEX_ARRAY[v shr 4]
        chars[i * 2 + 1] = HEX_ARRAY[v.and(0x0F)]
    }
    return String(chars)
}
package com.hash.tooltemplate.ui

import android.net.Uri

private const val SCHEME = "app://"

/**
 * add you custom navigation here
 */
sealed class NavigationScheme(private val hostAndPath: String) {
    val uri: Uri = Uri.parse("$SCHEME$hostAndPath")

    /**
     * eg
     * the result uri is app://test
     */
    object Test : NavigationScheme("test")

    object TestDialog:NavigationScheme("test_dialog")
}
package com.hash.template.utils

import android.content.Context
import android.content.SharedPreferences

object SPUtil {
    private const val SP_NAME = "config"
    private var sharedPreferences: SharedPreferences? = null
    private val sp: SharedPreferences
        get() = sharedPreferences!!

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    fun put(key: String, value: String) {
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String, defValue: String = ""): String {
        return sp.getString(key, defValue)!!
    }

    fun put(key: String, value: Long) {
        sp.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defValue: Long = 0L): Long {
        return sp.getLong(key, defValue)
    }
}
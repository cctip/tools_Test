package com.relicroverblack.relicrovergo.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonHelper {
    private val gson: Gson by lazy { GsonBuilder().create() }

    fun <T> fromJsonString(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    fun toJsonString(obj: Any): String {
        return gson.toJson(obj)
    }
}
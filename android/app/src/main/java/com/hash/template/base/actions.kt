package com.hash.template.base

internal data class Action<K1>(val value: K1)

internal data class Action2<K1, K2>(val k1: K1, val k2: K2)

internal data class Action3<K1, K2, K3>(
    val k1: K1,
    val k2: K2,
    val k3: K3
)
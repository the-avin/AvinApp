package com.avin.avinapp.preferences

sealed class PreferencesKey<T>(val itemKey: String) {
    data class StringKey(val key: String) : PreferencesKey<String>(key)
    data class IntKey(val key: String) : PreferencesKey<Int>(key)
    data class DoubleKey(val key: String) : PreferencesKey<Double>(key)
    data class BoolKey(val key: String) : PreferencesKey<BoolKey>(key)
}
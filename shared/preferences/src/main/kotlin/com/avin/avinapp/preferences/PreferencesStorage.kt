package com.avin.avinapp.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesStorage {
    fun <T> get(key: PreferencesKey<T>): Flow<T?>
    suspend fun <T> set(key: PreferencesKey<T>, value: T)
}
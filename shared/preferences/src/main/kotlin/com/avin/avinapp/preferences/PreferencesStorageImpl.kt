package com.avin.avinapp.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences
import com.avin.avinapp.utils.AppInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesStorageImpl : PreferencesStorage {
    private val dataStore: DataStore<Preferences> by lazy { createDatastore() }

    private fun createDatastore() = PreferenceDataStoreFactory.create(
        produceFile = { AppInfo.getAppFolderFile() }
    )

    override fun <T> get(key: PreferencesKey<T>): Flow<T?> {
        return dataStore.data.map { preferences ->
            when (key) {
                is PreferencesKey.StringKey -> preferences[stringPreferencesKey(key.key)] as? T
                is PreferencesKey.BoolKey -> preferences[booleanPreferencesKey(key.key)] as? T
                is PreferencesKey.IntKey -> preferences[intPreferencesKey(key.key)] as? T
                is PreferencesKey.DoubleKey -> preferences[doublePreferencesKey(key.key)] as? T
                else -> error("Unsupported preference type: ${key::class}")
            }
        }
    }

    override suspend fun <T> set(key: PreferencesKey<T>, value: T) {
        dataStore.edit { preferences ->
            when (key) {
                is PreferencesKey.StringKey -> preferences[stringPreferencesKey(key.key)] = value as String
                is PreferencesKey.BoolKey -> preferences[booleanPreferencesKey(key.key)] = value as Boolean
                is PreferencesKey.IntKey -> preferences[intPreferencesKey(key.key)] = value as Int
                is PreferencesKey.DoubleKey -> preferences[doublePreferencesKey(key.key)] = value as Double
                else -> error("Unsupported preference type: ${key::class}")
            }
        }
    }
}

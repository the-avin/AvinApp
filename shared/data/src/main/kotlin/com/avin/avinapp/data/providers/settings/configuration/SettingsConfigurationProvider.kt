package com.avin.avinapp.data.providers.settings.configuration

import com.avin.avinapp.data.models.settings.page.SettingsPage
import com.avin.avinapp.preferences.PreferencesKey

interface SettingsConfigurationProvider {
    val settingsPages: List<SettingsPage>

    companion object Keys {
        val themeKey = PreferencesKey.BoolKey("theme")
    }
}
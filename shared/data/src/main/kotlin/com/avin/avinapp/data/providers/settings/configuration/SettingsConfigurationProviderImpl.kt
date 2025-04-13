package com.avin.avinapp.data.providers.settings.configuration

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.avin.avinapp.data.models.settings.config.SettingsConfiguration
import com.avin.avinapp.data.models.settings.page.SettingsPage
import com.avin.avinapp.data.models.settings.config.SettingsType
import com.avin.avinapp.locale.StringRes
import com.avin.avinapp.preferences.PreferencesKey
import com.avin.avinapp.preferences.PreferencesStorage
import com.avin.avinapp.resource.Resource

class SettingsConfigurationProviderImpl(
    private val preferences: PreferencesStorage
) : SettingsConfigurationProvider {
    override val settingsPages: List<SettingsPage>
        get() = listOf(
            SettingsPage(
                name = Resource.string.general,
                configurations = generalConfigurations
            )
        )

    private val generalConfigurations: List<SettingsConfiguration<*>>
        get() = listOf(
            booleanSetting(
                name = Resource.string.theme,
                key = SettingsConfigurationProvider.Keys.themeKey,
                defaultValue = { isSystemInDarkTheme() }
            )
        )

    private fun booleanSetting(
        name: StringRes,
        key: PreferencesKey<Boolean>,
        defaultValue: @Composable () -> Boolean
    ): SettingsConfiguration<Boolean> {
        return SettingsConfiguration(
            name = name,
            initialValues = preferences.get(key),
            type = SettingsType.Checkbox,
            defaultValue = defaultValue,
            onValueChange = { preferences.set(key, it) }
        )
    }
}
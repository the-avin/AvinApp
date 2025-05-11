package com.avin.avinapp.data.providers.settings.configuration

import androidx.compose.runtime.Composable
import com.avin.avinapp.data.models.settings.config.SettingsConfiguration
import com.avin.avinapp.data.models.settings.page.SettingsPage
import com.avin.avinapp.data.models.settings.config.SettingsType
import com.avin.avinapp.locale.StringRes
import com.avin.avinapp.manager.compose.dynamicStringRes
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
            ),
            SettingsPage(
                name = Resource.string.editor,
                configurations = editorConfigurations
            ),
        )

    private val generalConfigurations: List<SettingsConfiguration<*>>
        get() = listOf(
            enumSetting(
                name = Resource.string.theme,
                key = SettingsConfigurationProvider.Keys.themeKey,
                defaultValue = { dynamicStringRes(Resource.string.system) },
                values = listOf(
                    Resource.string.system,
                    Resource.string.light,
                    Resource.string.dark,
                )
            )
        )

    private val editorConfigurations: List<SettingsConfiguration<*>>
        get() = listOf(
            booleanSetting(
                name = Resource.string.inspectOnHover,
                key = SettingsConfigurationProvider.Keys.inspectOnHover,
                defaultValue = { true },
                hint = { _ ->
                    dynamicStringRes(Resource.string.inspectOnHoverHint)
                }
            )
        )

    private fun booleanSetting(
        name: StringRes,
        key: PreferencesKey<Boolean>,
        defaultValue: @Composable () -> Boolean,
        hint: (@Composable (Boolean) -> String)? = null,
    ): SettingsConfiguration<Boolean> {
        return SettingsConfiguration(
            name = name,
            initialValues = preferences.get(key),
            type = SettingsType.Checkbox,
            defaultValue = defaultValue,
            onValueChange = { preferences.set(key, it) },
            hint = hint?.let { { hint.invoke(it as Boolean) } }
        )
    }

    private fun enumSetting(
        name: StringRes,
        key: PreferencesKey<String>,
        defaultValue: @Composable () -> String,
        values: List<StringRes>
    ): SettingsConfiguration<String> {
        return SettingsConfiguration(
            name = name,
            initialValues = preferences.get(key),
            type = SettingsType.Enum(values),
            defaultValue = defaultValue,
            onValueChange = { preferences.set(key, it) }
        )
    }
}
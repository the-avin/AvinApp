package com.avin.avinapp.data.models.settings.page

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.models.settings.config.SettingsConfiguration
import com.avin.avinapp.locale.StringRes

@Immutable
data class SettingsPage(
    val name: StringRes,
    val configurations: List<SettingsConfiguration<*>>
)
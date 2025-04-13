package com.avin.avinapp.settings.component

import com.arkivanov.decompose.ComponentContext
import com.avin.avinapp.components.BaseComponent
import com.avin.avinapp.data.models.settings.config.SettingsConfiguration
import com.avin.avinapp.data.models.settings.page.SettingsPage
import com.avin.avinapp.data.providers.settings.configuration.SettingsConfigurationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsComponent(
    context: ComponentContext,
    val provider: SettingsConfigurationProvider
) : BaseComponent(context) {
    val pages = provider.settingsPages

    private val _currentPage = MutableStateFlow(pages.first())
    val currentPage = _currentPage.asStateFlow()

    fun changePage(page: SettingsPage) {
        _currentPage.update { page }
    }

    fun <T : Any> updateValue(
        config: SettingsConfiguration<T>,
        value: Any
    ) = scope.launch(Dispatchers.IO) {
        config.onValueChange.invoke(value as T)
    }
}
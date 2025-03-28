package com.avin.avinapp.manager.language

import com.avin.avinapp.locale.StringRes
import com.avin.avinapp.manager.container.LanguageContainer
import com.avin.avinapp.manager.container.LanguageContainerImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LanguageManagerImpl : LanguageManager {
    private val _container = MutableStateFlow<LanguageContainer?>(null)
    val container = _container.asStateFlow()

    override fun load(language: String?) {
        val newContainer = LanguageContainerImpl().apply { loadLanguage(language) }
        _container.update { newContainer }
    }

    override fun getText(resId: StringRes): String {
        return _container.value?.getText(resId) ?: error("Container not defined")
    }
}
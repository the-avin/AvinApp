package com.avin.avinapp.manager.language

import com.avin.avinapp.locale.StringRes
import com.avin.avinapp.manager.container.LanguageContainer
import kotlinx.coroutines.flow.StateFlow

interface LanguageManager {
    val container: StateFlow<LanguageContainer?>

    fun load(language: String? = null)
    fun getText(resId: StringRes): String
}
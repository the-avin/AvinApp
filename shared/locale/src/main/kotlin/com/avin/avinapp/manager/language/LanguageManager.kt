package com.avin.avinapp.manager.language

import com.avin.avinapp.locale.StringRes

interface LanguageManager {
    fun load(language: String? = null)
    fun getText(resId: StringRes): String
}
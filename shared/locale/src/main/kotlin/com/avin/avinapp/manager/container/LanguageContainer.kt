package com.avin.avinapp.manager.container

import com.avin.avinapp.locale.StringRes

interface LanguageContainer {
    var currentLanguage: String?

    fun loadLanguage(lang: String?)
    fun getText(res: StringRes): String
}
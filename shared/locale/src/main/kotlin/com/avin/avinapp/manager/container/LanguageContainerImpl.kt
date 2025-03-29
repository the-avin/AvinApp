package com.avin.avinapp.manager.container

import com.avin.avinapp.locale.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.*

class LanguageContainerImpl : LanguageContainer {
    private val _translations = MutableStateFlow<Map<String, String>>(emptyMap())

    override var currentLanguage: String? = null


    override fun loadLanguage(lang: String?) {
        currentLanguage = lang
        val properties = Properties()
        val resourcePath = "${LOCALE_FOLDER}/${lang ?: DEFAULT_LANGUAGE}.properties"

        try {
            val stream = this::class.java.classLoader.getResourceAsStream(resourcePath)
            stream?.use { properties.load(it) }
            _translations.update { properties.entries.associate { it.key.toString() to it.value.toString() } }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getText(resId: StringRes): String {
        return _translations.value.getOrDefault(resId.resId, resId.resId)
    }

    companion object {
        private const val DEFAULT_LANGUAGE = "en"
        private const val LOCALE_FOLDER = "locales"
    }
}
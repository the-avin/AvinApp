package com.avin.avinapp.manager.compose

import androidx.compose.runtime.*
import com.avin.avinapp.manager.language.LanguageManager
import com.avin.avinapp.manager.language.LanguageManagerImpl

val LocalLanguageManager = staticCompositionLocalOf<LanguageManager> { error("Language Manager not initialized.") }

@Composable
fun rememberLocalLanguageManager(language: String?): LanguageManager {
    val manager = remember { LanguageManagerImpl().apply { load(language) } }
    LaunchedEffect(language) {
        if (language != manager.container.value?.currentLanguage) {
            manager.load(language)
        }
    }
    return manager
}

@Composable
fun WithLocaleLanguageManager(language: String? = null, content: @Composable () -> Unit) {
    val manager = rememberLocalLanguageManager(language)
    CompositionLocalProvider(LocalLanguageManager provides manager, content = content)
}
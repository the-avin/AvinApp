package com.avin.avinapp.manager.compose

import com.avin.avinapp.locale.StringRes
import androidx.compose.runtime.*

@Composable
fun dynamicStringRes(res: StringRes): String {
    val languageManager = LocalLanguageManager.current
    val container by languageManager.container.collectAsState()

    return remember(container) { languageManager.getText(res) }
}
package com.avin.avinapp.manager.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.avin.avinapp.locale.StringRes

@Composable
fun dynamicStringRes(res: StringRes): String {
    val languageManager = LocalLanguageManager.current
    return remember(res, languageManager) { languageManager.getText(res) }
}
package com.avin.avinapp.settings.window

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.theme.window.AppCustomWindow

@Composable
fun SettingsWindow(onCloseRequest: () -> Unit) {
    AppCustomWindow(
        onCloseRequest = onCloseRequest,
        state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
        )
    ) {

    }
}
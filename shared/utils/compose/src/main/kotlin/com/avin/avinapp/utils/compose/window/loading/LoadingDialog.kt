package com.avin.avinapp.utils.compose.window.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.rememberDialogState
import com.avin.avinapp.utils.compose.modifier.windowBackground

@Composable
fun LoadingDialog(
    onCloseRequest: () -> Unit = {},
    currentMessage: String? = null
) {
    DialogWindow(
        onCloseRequest = onCloseRequest,
        state = rememberDialogState(
            width = 200.dp,
            height = 64.dp
        )
    ) {
        Box(Modifier.fillMaxSize().windowBackground())
    }
}
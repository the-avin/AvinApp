package com.avin.avinapp.theme.buttons

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.OutlinedButton

@Composable
fun SecondaryButton(onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    DefaultButton(
        onClick = onClick,
        content = content,
    )
}
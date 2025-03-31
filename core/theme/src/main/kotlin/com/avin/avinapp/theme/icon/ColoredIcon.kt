package com.avin.avinapp.theme.icon

import androidx.compose.runtime.Composable
import org.jetbrains.jewel.foundation.theme.LocalContentColor
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.painterResource

@Composable
fun ColoredIcon(
    path: String,
) {
    Icon(
        painter = painterResource(path),
        contentDescription = null,
        tint = LocalContentColor.current
    )
}
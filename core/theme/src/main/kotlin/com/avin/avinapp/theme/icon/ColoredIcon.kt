package com.avin.avinapp.theme.icon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.jewel.foundation.theme.LocalContentColor
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.painterResource

@Composable
fun ColoredIcon(
    path: String,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(path),
        contentDescription = null,
        tint = LocalContentColor.current,
        modifier = modifier
    )
}
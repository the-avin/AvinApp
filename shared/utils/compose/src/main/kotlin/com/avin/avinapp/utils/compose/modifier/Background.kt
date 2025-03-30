package com.avin.avinapp.utils.compose.modifier

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.jewel.foundation.theme.JewelTheme

@Composable
fun Modifier.windowBackground() = background(
    JewelTheme.globalColors.panelBackground
)
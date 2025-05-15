package com.avin.avinapp.theme.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.jewel.foundation.theme.JewelTheme

val JewelTheme.Companion.hoverColor: Color
    @Composable
    get() = contentColor.copy(.1f)
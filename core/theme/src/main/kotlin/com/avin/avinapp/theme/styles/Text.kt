package com.avin.avinapp.theme.styles

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.createDefaultTextStyle

val JewelTheme.Companion.panelTitleTextStyle: TextStyle
    @Composable get() = JewelTheme.createDefaultTextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )
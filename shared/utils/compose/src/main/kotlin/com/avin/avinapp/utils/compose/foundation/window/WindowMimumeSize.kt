package com.avin.avinapp.utils.compose.foundation.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import java.awt.Dimension
import java.awt.Window

@Composable
fun Window.ApplyWindowMinimumSize(width: Int = 600, height: Int = 400) {
    LaunchedEffect(Unit) {
        minimumSize = Dimension(width, height)
    }
}
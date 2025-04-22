package com.avin.avinapp.preview.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

// Calculates scale factors between image and device sizes
fun calculateScale(fromSize: Size, toSize: Size): Offset {
    val scaleX = toSize.width / fromSize.width
    val scaleY = toSize.height / fromSize.height
    return Offset(scaleX, scaleY)
}
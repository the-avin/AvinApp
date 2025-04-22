package com.avin.avinapp.preview.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

// Calculates scale factors between image and device sizes
fun calculateScale(fromSize: Size, toSize: Size): Offset {
    val scaleX = toSize.width / fromSize.width
    val scaleY = toSize.height / fromSize.height
    return Offset(scaleX, scaleY)
}

fun calculateScaledOffset(fromSize: Size, toSize: Size, offset: Offset): Offset {
    val (scaleX, scaleY) = calculateScale(fromSize, toSize)

    val scaledOffsetX = offset.x * scaleX
    val scaledOffsetY = offset.y * scaleY

    return Offset(scaledOffsetX, scaledOffsetY)
}
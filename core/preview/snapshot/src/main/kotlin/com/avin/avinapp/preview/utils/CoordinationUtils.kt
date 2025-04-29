package com.avin.avinapp.preview.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

internal fun mapPointerToDevice(
    pointer: Offset,
    imageSize: Size,
    deviceSize: Size
): Offset {
    val scale = calculateScale(imageSize, deviceSize)
    return Offset(pointer.x * scale.x, pointer.y * scale.y)
}
package com.avin.avinapp.utils.compose.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntSize

val Size.aspectRatio: Float
    get() = width / height

val IntSize.aspectRatio: Float
    get() = width / height.toFloat()
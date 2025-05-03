package com.avin.avinapp.preview.extensions

import androidx.compose.ui.graphics.Color

fun String.toComposeColor() = Color(removePrefix("#").toLong(16) or 0x00000000FF000000)

fun String.toComposeColorCaching() = runCatching {
    toComposeColor()
}.getOrNull()

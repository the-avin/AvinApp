package com.avin.avinapp.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

@Immutable
data class RenderedComponentInfo(
    val id: String,
    val size: Size,
    val position: Offset
)

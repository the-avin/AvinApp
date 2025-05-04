package com.avin.avinapp.preview.data.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size

@Immutable
data class RenderedComponentInfo(
    val id: String,
    val position: Offset = Offset.Zero,
    val size: Size = Size.Zero
)

val RenderedComponentInfo.rect: Rect
    get() = Rect(position, size)

fun List<RenderedComponentInfo>.findComponentById(id: String) = find { it.id == id }
fun List<RenderedComponentInfo>.findTopMostComponentByPosition(position: Offset) =
    findLast { it.rect.contains(position) }


val RenderedComponentInfo.formattedText: String
    get() = "{$id} ${size.width.formatSmart()}px:${size.height.formatSmart()}px"

fun Float.formatSmart(): String =
    if (this % 1f == 0f) this.toInt().toString() else String.format("%.1f", this)
package com.avin.avinapp.utils.compose.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint

fun Modifier.grayscale(): Modifier = this.then(
    Modifier.drawWithContent {
        val colorMatrix = ColorMatrix().apply { setToSaturation(0f) }
        with(drawContext.canvas) {
            val rect = Rect(Offset.Zero, size)
            saveLayer(rect, Paint().apply {
                colorFilter = ColorFilter.colorMatrix(colorMatrix)
            })
        }
        drawContent()
        drawContext.canvas.restore()
    }
)

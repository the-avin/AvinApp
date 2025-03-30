package com.avin.avinapp.utils.compose.nodes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp

@Composable
fun DrawInitialsWithCanvas(text: String) {
    val textMeasurer = rememberTextMeasurer()
    val initials = text.split(" ")
        .take(2)
        .joinToString("") { it.firstOrNull()?.toString().orEmpty() }
    if (initials.isNotEmpty()) {
        val backgroundColor = when {
            initials.firstOrNull()?.isLetter() == true -> Color(
                (initials.first().code * 3 + 123) % 256,
                (initials.first().code * 5 + 45) % 256,
                (initials.first().code * 7 + 32) % 256
            )

            else -> Color.Gray
        }
        Canvas(modifier = Modifier.size(24.dp)) {
            drawRoundRect(
                color = backgroundColor,
                size = size,
                cornerRadius = CornerRadius(2.dp.toPx())
            )
            val style = TextStyle(
                color = Color.White, fontSize = size.height.toSp(), fontWeight = FontWeight.Light
            )
            val textResult = textMeasurer.measure(
                initials, style = style
            )

            val offset = Offset(
                size.width.minus(textResult.size.width).div(2),
                size.height.minus(textResult.size.height).div(2) - textResult.size.height.div(11),
            )
            drawText(
                textResult, topLeft = offset
            )
        }
    }
}

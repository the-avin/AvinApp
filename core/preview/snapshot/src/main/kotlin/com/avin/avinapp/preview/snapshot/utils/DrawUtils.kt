package com.avin.avinapp.preview.snapshot.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.avin.avinapp.preview.data.models.RenderedComponentInfo
import com.avin.avinapp.preview.data.models.formattedText
import com.avin.avinapp.preview.utils.calculateScale

internal fun DrawScope.drawComponentHighlight(
    component: RenderedComponentInfo,
    imageSize: Size,
    deviceSize: Size,
    color: Color = Color.Red
) {
    // Inverse scale to convert from device coordinates back to image coordinates
    val inverseScale = calculateScale(deviceSize, imageSize)
    val topLeft =
        Offset(component.position.x * inverseScale.x, component.position.y * inverseScale.y)
    val size = Size(component.size.width * inverseScale.x, component.size.height * inverseScale.y)
    drawRect(
        color,
        topLeft,
        size,
        style = Stroke(2.dp.toPx())
    )
}

internal fun DrawScope.drawComponentHighlightInfo(
    component: RenderedComponentInfo,
    imageSize: Size,
    deviceSize: Size,
    textMeasurer: TextMeasurer,
    style: TextStyle = TextStyle(fontSize = 12.sp, color = Color.White),
    color: Color = Color.Red,
    horizontalPadding: Float = 8f,
    verticalPadding: Float = 4f,
    strokeWidth: Dp = 2.dp
) {
    val inverseScale = calculateScale(deviceSize, imageSize)

    val highlightTopLeft = Offset(
        x = component.position.x * inverseScale.x,
        y = component.position.y * inverseScale.y
    )

    val result = textMeasurer.measure(text = component.formattedText, style = style)
    val textSize = result.size.toSize()

    val textTopLeft = Offset(
        x = highlightTopLeft.x + (component.size.width * inverseScale.x) - textSize.width - horizontalPadding
                + strokeWidth.toPx().div(2),
        y = highlightTopLeft.y - textSize.height - verticalPadding
    )

    drawRect(
        color = color,
        topLeft = textTopLeft - Offset(horizontalPadding, verticalPadding),
        size = Size(
            width = textSize.width + 2 * horizontalPadding,
            height = textSize.height + 2 * verticalPadding
        )
    )

    drawText(
        textLayoutResult = result,
        topLeft = textTopLeft
    )
}

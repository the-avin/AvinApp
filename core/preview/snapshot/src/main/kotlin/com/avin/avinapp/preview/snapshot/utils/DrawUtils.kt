package com.avin.avinapp.preview.snapshot.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.avin.avinapp.preview.data.models.RenderedComponentInfo
import com.avin.avinapp.preview.data.models.formatSmart
import com.avin.avinapp.preview.data.models.formattedText
import com.avin.avinapp.preview.utils.calculateScale

internal fun DrawScope.drawComponentHighlight(
    component: RenderedComponentInfo,
    imageSize: Size,
    deviceSize: Size,
    color: Color = Color.Blue.copy(.2f)
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
    )
}

internal fun DrawScope.drawComponentHighlightBordered(
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

private fun componentBounds(
    component: RenderedComponentInfo,
    imageSize: Size,
    deviceSize: Size
): Pair<Offset, Size> {
    val scale = calculateScale(deviceSize, imageSize)
    val topLeft = Offset(component.position.x * scale.x, component.position.y * scale.y)
    val size = Size(component.size.width * scale.x, component.size.height * scale.y)
    return topLeft to size
}

private fun DrawScope.drawTextWithBackground(
    textLayoutResult: TextLayoutResult,
    center: Offset,
    color: Color,
    horizontalPadding: Float,
    verticalPadding: Float,
    avoidRect: Rect
) {
    val textSize = textLayoutResult.size.toSize()
    var topLeft = Offset(
        x = center.x - textSize.width / 2 - horizontalPadding,
        y = center.y - textSize.height / 2 - verticalPadding
    )
    val backgroundSize = Size(
        width = textSize.width + horizontalPadding * 2,
        height = textSize.height + verticalPadding * 2
    )
    var backgroundRect = Rect(topLeft, backgroundSize)

    if (backgroundRect.overlaps(avoidRect)) {
        val centerX = center.x
        val centerY = center.y

        val avoidCenter = avoidRect.center

        if (centerY in avoidRect.top..avoidRect.bottom) {
            topLeft = if (centerX < avoidCenter.x) {
                Offset(x = avoidRect.left - backgroundSize.width - 8f, y = topLeft.y)
            } else {
                Offset(x = avoidRect.right + 8f, y = topLeft.y)
            }
        } else if (centerX in avoidRect.left..avoidRect.right) {
            topLeft = if (centerY < avoidCenter.y) {
                Offset(x = topLeft.x, y = avoidRect.top - backgroundSize.height - 8f)
            } else {
                Offset(x = topLeft.x, y = avoidRect.bottom + 8f)
            }
        }

        backgroundRect = Rect(topLeft, backgroundSize)
    }

    drawRect(color = color, topLeft = topLeft, size = backgroundSize)
    drawText(textLayoutResult, topLeft = topLeft + Offset(horizontalPadding, verticalPadding))
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
    val (highlightTopLeft, size) = componentBounds(component, imageSize, deviceSize)
    val result = textMeasurer.measure(text = component.formattedText, style = style)
    val textSize = result.size.toSize()

    val textTopLeft = Offset(
        x = highlightTopLeft.x + size.width - textSize.width - horizontalPadding + strokeWidth.toPx() / 2,
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

    drawText(result, topLeft = textTopLeft)
}

internal fun DrawScope.drawComponentGuidesWithDistances(
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
    val (topLeft, size) = componentBounds(component, imageSize, deviceSize)
    val bottomRight = topLeft + Offset(size.width, size.height)
    val centerX = topLeft.x + size.width / 2
    val centerY = topLeft.y + size.height / 2

    val stroke = Stroke(
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
    )

    val topEnd = Offset(centerX, 0f)
    val bottomEnd = Offset(centerX, size.height.coerceAtLeast(this.size.height))
    val leftEnd = Offset(0f, centerY)
    val rightEnd = Offset(this.size.width, centerY)

    val componentRect = Rect(topLeft, size)

    drawLine(
        color,
        Offset(centerX, topLeft.y),
        topEnd,
        strokeWidth = stroke.width,
        pathEffect = stroke.pathEffect,
        cap = StrokeCap.Round
    )
    drawLine(
        color,
        Offset(centerX, bottomRight.y),
        bottomEnd,
        strokeWidth = stroke.width,
        pathEffect = stroke.pathEffect,
        cap = StrokeCap.Round
    )
    drawLine(
        color,
        Offset(topLeft.x, centerY),
        leftEnd,
        strokeWidth = stroke.width,
        pathEffect = stroke.pathEffect,
        cap = StrokeCap.Round
    )
    drawLine(
        color,
        Offset(bottomRight.x, centerY),
        rightEnd,
        strokeWidth = stroke.width,
        pathEffect = stroke.pathEffect,
        cap = StrokeCap.Round
    )

    val topDistance = component.position.y
    val bottomDistance = deviceSize.height - component.position.y - component.size.height
    val leftDistance = component.position.x
    val rightDistance = deviceSize.width - component.position.x - component.size.width

    drawTextWithBackground(
        textMeasurer.measure("${topDistance.formatSmart()} px", style),
        center = Offset(centerX, topLeft.y / 2),
        color, horizontalPadding, verticalPadding, componentRect
    )
    drawTextWithBackground(
        textMeasurer.measure("${bottomDistance.formatSmart()} px", style),
        center = Offset(centerX, bottomRight.y + (bottomEnd.y - bottomRight.y) / 2),
        color, horizontalPadding, verticalPadding, componentRect
    )
    drawTextWithBackground(
        textMeasurer.measure("${leftDistance.formatSmart()} px", style),
        center = Offset(topLeft.x / 2, centerY),
        color, horizontalPadding, verticalPadding, componentRect
    )
    drawTextWithBackground(
        textMeasurer.measure("${rightDistance.formatSmart()} px", style),
        center = Offset(bottomRight.x + (rightEnd.x - bottomRight.x) / 2, centerY),
        color, horizontalPadding, verticalPadding, componentRect
    )
}
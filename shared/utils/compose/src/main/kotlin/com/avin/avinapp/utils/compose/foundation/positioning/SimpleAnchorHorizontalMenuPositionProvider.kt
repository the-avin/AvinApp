package com.avin.avinapp.utils.compose.foundation.positioning

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider

@Immutable
data class SimpleAnchorHorizontalMenuPositionProvider(
    val contentOffset: DpOffset,
    val alignment: Alignment.Vertical,
    val density: Density,
) : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val offsetX = with(density) {
            contentOffset.x.roundToPx() * if (layoutDirection == LayoutDirection.Ltr) 1 else -1
        }
        val offsetY = with(density) { contentOffset.y.roundToPx() }

        val y = anchorBounds.top + alignment.align(
            popupContentSize.height,
            anchorBounds.height
        ) + offsetY

        val x = anchorBounds.right + offsetX

        return IntOffset(x, y)
    }
}

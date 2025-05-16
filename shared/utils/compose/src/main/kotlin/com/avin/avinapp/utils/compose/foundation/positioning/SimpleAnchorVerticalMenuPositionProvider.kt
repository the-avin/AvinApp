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
data class SimpleAnchorVerticalMenuPositionProvider(
    val contentOffset: DpOffset,
    val alignment: Alignment.Horizontal,
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

        // Horizontal position: align to anchor width using horizontal alignment
        val x = anchorBounds.left +
                alignment.align(popupContentSize.width, anchorBounds.width, layoutDirection) +
                offsetX

        // Vertical position: try to show below anchor, fallback to above
        val spaceBelow = windowSize.height - anchorBounds.bottom - offsetY
        val spaceAbove = anchorBounds.top - offsetY

        val y = if (spaceBelow >= popupContentSize.height || spaceBelow >= spaceAbove) {
            anchorBounds.bottom + offsetY
        } else {
            anchorBounds.top - offsetY - popupContentSize.height
        }

        return IntOffset(x, y)
    }
}
package com.avin.avinapp.utils.compose.foundation.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Horizontal
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun Arrangement.endWithCustomSpace(spacing: Dp = 12.dp) = object : Horizontal {
    override val spacing: Dp = spacing

    override fun Density.arrange(
        totalSize: Int,
        sizes: IntArray,
        layoutDirection: LayoutDirection,
        outPositions: IntArray
    ) {
        if (layoutDirection == LayoutDirection.Ltr) {
            placeRightOrBottom(totalSize, sizes, outPositions, spacing)
        } else {
            placeLeftOrTop(sizes, outPositions, spacing)
        }
    }

    override fun toString() = "Arrangement#EndWithCustomSpace"
}

fun Arrangement.startWithCustomSpace(spacing: Dp = 12.dp) = object : Horizontal {
    override val spacing: Dp = spacing

    override fun Density.arrange(
        totalSize: Int,
        sizes: IntArray,
        layoutDirection: LayoutDirection,
        outPositions: IntArray
    ) {
        if (layoutDirection == LayoutDirection.Ltr) {
            placeLeftOrTop(sizes, outPositions, spacing)
        } else {
            placeRightOrBottom(totalSize, sizes, outPositions, spacing)
        }
    }

    override fun toString() = "Arrangement#StartWithCustomSpace"
}

private fun Density.placeRightOrBottom(
    totalSize: Int,
    sizes: IntArray,
    outPositions: IntArray,
    spacing: Dp
) {
    val consumedSize = sizes.sum() + (sizes.size - 1) * spacing.roundToPx()
    var current = totalSize - consumedSize
    sizes.forEachIndexed { index, size ->
        outPositions[index] = current
        current += size + spacing.roundToPx()
    }
}

private fun Density.placeLeftOrTop(
    sizes: IntArray,
    outPositions: IntArray,
    spacing: Dp
) {
    var current = 0
    sizes.forEachIndexed { index, size ->
        outPositions[index] = current
        current += size + spacing.roundToPx()
    }
}

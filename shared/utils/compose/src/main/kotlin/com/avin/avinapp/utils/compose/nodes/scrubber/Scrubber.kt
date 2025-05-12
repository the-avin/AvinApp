package com.avin.avinapp.utils.compose.nodes.scrubber

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.icons.AllIconsKeys

@Composable
fun Scrubber(
    sensitivity: Float = 2f,
    iconWidth: Dp = 12.dp,
    orientation: Orientation = Orientation.Vertical,
    onScrub: (Float) -> Unit
) {
    val iconHeight = iconWidth / 2f
    val ins = remember { MutableInteractionSource() }
    val isHovered by ins.collectIsHoveredAsState()
    val contentColor = JewelTheme.contentColor
    val color = when {
        isHovered -> contentColor
        else -> contentColor.copy(.7f)
    }
    val onScrub by rememberUpdatedState(onScrub)
    val reverseDirection = when (orientation) {
        Orientation.Horizontal -> LocalLayoutDirection.current == LayoutDirection.Rtl
        else -> true
    }
    Column(
        modifier = Modifier
            .hoverable(ins)
            .height(iconHeight.times(2))
            .draggable(
                rememberDraggableState {
                    onScrub.invoke(it / sensitivity)
                },
                reverseDirection = reverseDirection,
                orientation = orientation
            )
    ) {
        Box(Modifier.weight(1f)) {
            Icon(
                AllIconsKeys.General.ArrowUp,
                contentDescription = null,
                modifier = Modifier
                    .offset(y = (-1).dp)
                    .width(iconWidth)
                    .wrapContentSize(unbounded = true),
                tint = color
            )
        }
        Box(Modifier.weight(1f)) {
            Icon(
                AllIconsKeys.General.ArrowDown,
                contentDescription = null,
                modifier = Modifier
                    .offset(y = 1.dp)
                    .width(iconWidth)
                    .wrapContentSize(unbounded = true),
                tint = color
            )
        }
    }
}
package com.avin.avinapp.utils.compose.nodes.expandable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.icons.AllIconsKeys
import org.jetbrains.jewel.ui.theme.simpleListItemStyle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExpandableTitle(title: String, isExpanded: Boolean, onExpandChanged: (Boolean) -> Unit) {
    val style = JewelTheme.simpleListItemStyle
    val ins = remember { MutableInteractionSource() }
    val isHovered by ins.collectIsHoveredAsState()
    val color = when {
        isHovered -> JewelTheme.contentColor.copy(.1f)
        else -> Color.Transparent
    }
    val rotate by animateFloatAsState(if (isExpanded) 0f else 180f)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(JewelTheme.globalMetrics.rowHeight)
            .hoverable(ins)
            .onPointerEvent(PointerEventType.Press) {
                onExpandChanged.invoke(isExpanded.not())
            }
            .padding(style.metrics.outerPadding)
            .background(color, RoundedCornerShape(style.metrics.selectionBackgroundCornerSize))
            .padding(style.metrics.innerPadding),
    ) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = JewelTheme.defaultTextStyle,
            modifier = Modifier.weight(1f)
        )
        Icon(
            key = AllIconsKeys.General.ArrowUp,
            contentDescription = null,
            modifier = Modifier.rotate(rotate)
        )
    }
}
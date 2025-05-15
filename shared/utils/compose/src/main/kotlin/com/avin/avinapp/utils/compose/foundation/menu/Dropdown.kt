package com.avin.avinapp.utils.compose.foundation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.avin.avinapp.locale.StringRes
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.theme.extensions.hoverColor
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.foundation.theme.LocalContentColor
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.LocalMenuManager
import org.jetbrains.jewel.ui.component.MenuScope
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.painterResource
import org.jetbrains.jewel.ui.icon.IconKey
import org.jetbrains.jewel.ui.theme.simpleListItemStyle
import org.jetbrains.jewel.ui.util.thenIf

fun MenuScope.simpleItem(
    onClick: () -> Unit,
    isStartItem: Boolean = false,
    isEndItem: Boolean = false,
    isSelected: Boolean = false,
    content: @Composable () -> Unit
) {
    passiveItem {
        val menuManager = LocalMenuManager.current
        val style = JewelTheme.simpleListItemStyle
        val ins = remember { MutableInteractionSource() }
        val isHovered by ins.collectIsHoveredAsState()
        val color = when {
            isSelected -> style.colors.backgroundSelectedFocused
            isHovered -> JewelTheme.hoverColor
            else -> Color.Transparent
        }
        Box(
            Modifier
                .thenIf(isStartItem) {
                    padding(top = style.metrics.outerPadding.calculateEndPadding(LayoutDirection.Ltr))
                }
                .thenIf(isEndItem) {
                    padding(bottom = style.metrics.outerPadding.calculateEndPadding(LayoutDirection.Ltr))
                }
                .padding(style.metrics.outerPadding)
                .clip(RoundedCornerShape(style.metrics.selectionBackgroundCornerSize))
                .width(250.dp)
                .heightIn(min = JewelTheme.globalMetrics.rowHeight)
                .background(color)
                .hoverable(ins)
                .clickable(interactionSource = ins, indication = null) {
                    menuManager.close(InputMode.Touch)
                    onClick.invoke()
                }
                .padding(style.metrics.innerPadding),
            contentAlignment = Alignment.CenterStart
        ) { content.invoke() }
    }
}


fun <T> MenuScope.simpleItems(
    items: List<T>,
    onClick: (T) -> Unit,
    isSelected: (T) -> Boolean = { false },
    content: @Composable (T) -> Unit
) {
    items.forEachIndexed { index, item ->
        simpleItem(
            onClick = { onClick.invoke(item) },
            isStartItem = index == 0,
            isEndItem = index == items.size - 1,
            isSelected = isSelected.invoke(item)
        ) { content.invoke(item) }
    }
}

fun MenuScope.iconTextItem(
    stringRes: StringRes,
    icon: String,
    isStartItem: Boolean = false,
    isEndItem: Boolean = false,
    onClick: () -> Unit
) {
    simpleItem(onClick = onClick, isEndItem = isEndItem, isStartItem = isStartItem) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.pointerHoverIcon(
                PointerIcon.Hand
            )
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = LocalContentColor.current,
                modifier = Modifier.size(16.dp)
            )
            Text(dynamicStringRes(stringRes))
        }
    }
}


fun MenuScope.textItem(
    stringRes: StringRes,
    isStartItem: Boolean = false,
    isEndItem: Boolean = false,
    onClick: () -> Unit
) {
    simpleItem(onClick = onClick, isStartItem = isStartItem, isEndItem = isEndItem) {
        Box(
            modifier = Modifier.pointerHoverIcon(
                PointerIcon.Hand
            ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(dynamicStringRes(stringRes))
        }
    }
}

fun MenuScope.iconTextItem(
    stringRes: StringRes,
    icon: IconKey,
    isStartItem: Boolean = false,
    isEndItem: Boolean = false,
    onClick: () -> Unit
) {
    simpleItem(onClick = onClick, isStartItem = isStartItem, isEndItem = isEndItem) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                key = icon,
                contentDescription = null,
                tint = LocalContentColor.current,
                modifier = Modifier.size(16.dp)
            )
            Text(dynamicStringRes(stringRes))
        }
    }
}
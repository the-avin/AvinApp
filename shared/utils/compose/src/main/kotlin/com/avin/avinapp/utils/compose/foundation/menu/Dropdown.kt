package com.avin.avinapp.utils.compose.foundation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import com.avin.avinapp.locale.StringRes
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.theme.icon.ColoredIcon
import org.jetbrains.jewel.foundation.theme.LocalContentColor
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.MenuScope
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.painterResource
import org.jetbrains.jewel.ui.icon.IconKey

fun MenuScope.simpleItem(onClick: () -> Unit, content: @Composable () -> Unit) {
    selectableItem(false, onClick = onClick) {
        Box(Modifier.width(250.dp).padding(horizontal = 4.dp)) { content.invoke() }
    }
}

fun MenuScope.iconTextItem(
    stringRes: StringRes,
    icon: Painter,
    onClick: () -> Unit
) {
    simpleItem(onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.pointerHoverIcon(
                PointerIcon.Hand
            )
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = LocalContentColor.current,
                modifier = Modifier.size(16.dp)
            )
            Text(dynamicStringRes(stringRes))
        }
    }
}

fun MenuScope.iconTextItem(
    stringRes: StringRes,
    icon: IconKey,
    onClick: () -> Unit
) {
    simpleItem(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
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
package com.avin.avinapp.utils.compose.nodes.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.icon.ColoredIcon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.MenuScope
import org.jetbrains.jewel.ui.component.PopupMenu
import org.jetbrains.jewel.ui.component.styling.LocalDefaultDropdownStyle

@Composable
fun IconMenu(
    minWidth: Dp = 100.dp,
    menuContent: MenuScope.() -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = {
            expanded = true
        }) {
            ColoredIcon(
                Resource.image.MORE_VERT
            )
        }
        if (expanded) {
            PopupMenu(
                onDismissRequest = {
                    expanded = false
                    true
                },
                modifier =
                    Modifier
                        .focusProperties { canFocus = true }
                        .defaultMinSize(minWidth = minWidth),
                style = LocalDefaultDropdownStyle.current.menuStyle,
                horizontalAlignment = Alignment.Start,
                content = menuContent,
            )
        }
    }
}
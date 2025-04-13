package com.avin.avinapp.features.editor.dsl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.utils.compose.menu.buildMenuContent
import com.avin.avinapp.utils.compose.menu.renderContent
import com.avin.avinapp.utils.compose.nodes.menu.IconMenu

@Composable
fun EditorTitleBarAction(
    onOpenSettings: () -> Unit,
) {
    val menuContent = remember {
        buildMenuContent {
            +item(Resource.string.settings, onOpenSettings)
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        IconMenu {
            renderContent(menuContent)
        }
    }
}
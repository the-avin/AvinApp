package com.avin.avinapp.features.editor.dsl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.utils.compose.menu.buildMenuContent
import com.avin.avinapp.utils.compose.menu.renderContent
import com.avin.avinapp.utils.compose.nodes.menu.IconMenu

@Composable
fun EditorTitleBarAction(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
    ) {
        IconMenu {
            buildMenuContent {
                +submenu(Resource.string.open) {
                    +item(Resource.string.newProject, action = { println("hello world") })
                    +separator()
                    +item(Resource.string.newProject, action = { println("hello world") })
                }
                +separator()
                +item(Resource.string.newProject, action = { println("hello world") })
            }.also { renderContent(it) }
        }
    }
}
package com.avin.avinapp.settings.window

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.settings.component.SettingsComponent
import com.avin.avinapp.settings.dsl.ConfigurationItem
import com.avin.avinapp.settings.dsl.Sidebar
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.modifier.verticalPadding
import com.avin.avinapp.utils.compose.nodes.drag_handler.DragHandler
import org.jetbrains.jewel.ui.component.Text

@Composable
fun SettingsWindow(
    component: SettingsComponent,
    onCloseRequest: () -> Unit
) {
    val title = dynamicStringRes(Resource.string.settings)
    val currentPage by component.currentPage.collectAsState()
    val pages = component.pages
    AppCustomWindow(
        onCloseRequest = onCloseRequest,
        state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
        ),
        title = title
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            fun coerceWidth(width: Dp) = width.coerceIn(100.dp, 400.dp)
            var sidebarWidth by remember { mutableStateOf(coerceWidth(maxWidth * .3f)) }
            Row(modifier = Modifier.fillMaxSize()) {
                Sidebar(
                    currentPage = currentPage,
                    pages = pages,
                    onPageChange = component::changePage,
                    modifier = Modifier.width(sidebarWidth)
                )
                DragHandler(
                    orientation = Orientation.Horizontal,
                    onDrag = { sidebarWidth = coerceWidth(sidebarWidth + it) },
                    modifier = Modifier.verticalPadding()
                )
                LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.fillMaxWidth()) {
                    items(currentPage.configurations, key = { it.name.resId }) { configuration ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = dynamicStringRes(configuration.name),
                            )
                            ConfigurationItem(
                                configuration = configuration,
                                onValueChange = {
                                    component.updateValue(configuration, it)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
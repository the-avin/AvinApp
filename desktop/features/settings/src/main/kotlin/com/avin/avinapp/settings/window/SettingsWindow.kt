package com.avin.avinapp.settings.window

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.data.models.settings.config.SettingsConfiguration
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.settings.component.SettingsComponent
import com.avin.avinapp.settings.dsl.ConfigurationItem
import com.avin.avinapp.settings.dsl.Sidebar
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.foundation.window.ApplyWindowMinimumSize
import com.avin.avinapp.utils.compose.modifier.verticalPadding
import com.avin.avinapp.utils.compose.nodes.drag_handler.DragHandler
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.VerticallyScrollableContainer

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
        window.ApplyWindowMinimumSize()
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
                    onDrag = {
                        sidebarWidth = coerceWidth(sidebarWidth + it)
                    },
                    modifier = Modifier.verticalPadding()
                )
                SettingsConfigurations(
                    configurations = currentPage.configurations,
                    onValueChange = { config, value -> component.updateValue(config, value) },
                )
            }
        }
    }
}


@Composable
fun SettingsConfigurations(
    configurations: List<SettingsConfiguration<*>>,
    onValueChange: (SettingsConfiguration<*>, Any) -> Unit
) {
    val lazyState = rememberLazyListState()
    VerticallyScrollableContainer(scrollState = lazyState) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxWidth(),
            state = lazyState
        ) {
            items(configurations, key = { it.name.resId }) { configuration ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val value by configuration.initialValues.collectAsState(null)
                    val requiredValue = value ?: configuration.defaultValue.invoke()
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = dynamicStringRes(configuration.name),
                        )
                        configuration.hint?.let {
                            Text(
                                text = it.invoke(requiredValue),
                                fontSize = 10.sp,
                                modifier = Modifier.alpha(.7f)
                            )
                        }
                    }
                    ConfigurationItem(
                        configuration = configuration,
                        value = requiredValue,
                        onValueChange = {
                            onValueChange(configuration, it)
                        }
                    )
                }
            }
        }
    }
}
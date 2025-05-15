package com.avin.avinapp.settings.window

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
            Row(modifier = Modifier.fillMaxSize()) {
                Sidebar(
                    currentPage = currentPage,
                    pages = pages,
                    onPageChange = component::changePage,
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
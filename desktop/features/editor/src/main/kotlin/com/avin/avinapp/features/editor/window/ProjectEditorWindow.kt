package com.avin.avinapp.features.editor.window

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.features.editor.component.ProjectEditorComponent
import com.avin.avinapp.features.editor.data.pages.EditorPages
import com.avin.avinapp.features.editor.dsl.titlebar.ProjectEditorTitleBar
import com.avin.avinapp.preview.collector.rememberComponentRenderCollector
import com.avin.avinapp.preview.state.rememberSnapshotRenderState
import com.avin.avinapp.preview.widgets.SnapshotPreview
import com.avin.avinapp.theme.icon.ColoredIcon
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.foundation.window.ApplyWindowMinimumSize
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.ToggleableIconButton

@OptIn(InternalComposeUiApi::class)
@Composable
fun ProjectEditorWindow(
    component: ProjectEditorComponent,
    onOpenProject: (Long) -> Unit,
    onNewProjectClick: () -> Unit,
    onCloneRepositoryClick: () -> Unit,
    onOpenFilePicker: () -> Unit,
    onOpenSettings: () -> Unit,
    onCloseRequest: () -> Unit,
) {
    var count by remember { mutableStateOf(1) }
    val project by component.project.collectAsState()
    val projectName = project?.name.orEmpty()
    val recentProjects by component.recentProjects.collectAsState()
    val devices by component.devices.collectAsState()
    val currentPage by component.currentPage.collectAsState()
    val collector = rememberComponentRenderCollector()
    val rendererState = rememberSnapshotRenderState(
        devices = devices,
        collector = collector
    )
    AppCustomWindow(
        onCloseRequest = onCloseRequest,
        title = projectName,
        state = rememberWindowState(
            placement = WindowPlacement.Fullscreen,
            position = WindowPosition.Aligned(Alignment.Center),
            size = DpSize(1000.dp, 700.dp)
        )
    ) {
        window.ApplyWindowMinimumSize(800, 500)
        ProjectEditorTitleBar(
            projectName = projectName,
            devices = devices,
            currentPage = currentPage,
            currentDevice = rendererState.currentDevice,
            recentProjects = recentProjects,
            onNewProjectClick = onNewProjectClick,
            onOpenProject = onOpenProject,
            onOpenFilePicker = onOpenFilePicker,
            onCloneRepositoryClick = onCloneRepositoryClick,
            onOpenSettings = onOpenSettings,
            onDeviceSelected = rendererState::selectDevice
        )
        Row(modifier = Modifier.fillMaxSize()) {
            Sidebar(
                currentPage = currentPage,
                onPageChanged = component::changePage
            )
            Divider(Orientation.Vertical, modifier = Modifier.fillMaxHeight())

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (currentPage) {
                    is EditorPages.Screens -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            SnapshotPreview(
                                state = rendererState,
                                collector = collector,
                                modifier = Modifier.fillMaxHeight(.9f)
                            )
                            DefaultButton(onClick = {
                                count++
                                rendererState.renderPreview(
                                    JsonObject(
                                        mapOf("type" to JsonPrimitive(count))
                                    )
                                )
                            }) {
                                Text("Render")
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}


@Composable
fun Sidebar(
    currentPage: EditorPages,
    onPageChanged: (EditorPages) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxHeight().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        remember { EditorPages.pages }.forEach { page ->
            ToggleableIconButton(
                value = currentPage == page,
                onValueChange = { onPageChanged.invoke(page) },
                modifier = Modifier.size(28.dp)
            ) {
                ColoredIcon(page.icon, modifier = Modifier.size(18.dp))
            }
        }
    }
}
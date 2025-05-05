@file:OptIn(InternalComposeUiApi::class)

package com.avin.avinapp.features.editor.window

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.data.models.widget.ComposableDescriptor
import com.avin.avinapp.features.editor.data.pages.EditorPages
import com.avin.avinapp.features.editor.dsl.titlebar.ProjectEditorTitleBar
import com.avin.avinapp.features.editor.widgets.chooser.ProjectEditorComponent
import com.avin.avinapp.features.editor.widgets.properties.PropertiesBar
import com.avin.avinapp.preview.collector.rememberComponentRenderCollector
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.realtime.state.rememberRealtimeRenderState
import com.avin.avinapp.preview.realtime.widget.RealtimePreview
import com.avin.avinapp.preview.snapshot.state.rememberSnapshotRenderState
import com.avin.avinapp.preview.snapshot.widgets.SnapshotPreview
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.foundation.window.ApplyWindowMinimumSize
import com.avin.avinapp.utils.compose.nodes.navigation_bar.VerticalNavigationBar
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.Text

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
    val project by component.project.collectAsState()
    val projectName = project?.name.orEmpty()
    val recentProjects by component.recentProjects.collectAsState()
    val devices by component.devices.collectAsState()
    val currentPage by component.currentPage.collectAsState()
    val collector = rememberComponentRenderCollector()
    val rendererState = rememberSnapshotRenderState(
        devices = devices,
        collector = collector,
    )
    val holder = remember {
        val buttonDescriptor = ComposableDescriptor("material3.button", emptyList(), true)
        val textDescriptor = ComposableDescriptor("material3.text", emptyList(), false)
        ComposableStateHolder(buttonDescriptor).apply {
            addChild(
                ComposableStateHolder(textDescriptor).apply {
                    updateParameter("label", "Test 2")
                }
            )
        }
    }
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
            VerticalNavigationBar(
                currentPage = currentPage,
                items = remember { EditorPages.navigationPages },
                onPageChanged = component::changePage
            )
            Divider(Orientation.Vertical, modifier = Modifier.fillMaxHeight())

            Box(Modifier.fillMaxHeight().weight(1f), contentAlignment = Alignment.Center) {
                when (currentPage) {
                    is EditorPages.Screens -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            SnapshotPreview(
                                state = rendererState,
                                collector = collector,
                                modifier = Modifier.fillMaxHeight(.9f)
                            )
                            DefaultButton(onClick = {
                                rendererState.renderPreview(
                                    holder
                                )
                            }) {
                                Text("Render")
                            }
                        }
                    }

                    else -> {
                        RealtimePreviewSample(rendererState.currentDevice!!, holder)
                    }
                }
            }
            PropertiesBar(rendererState)
        }
    }
}

@Composable
fun RealtimePreviewSample(device: PreviewDevice, holder: ComposableStateHolder) {
    val rendererState = rememberRealtimeRenderState(
        device = device,
    )
    LaunchedEffect(Unit) {
        rendererState.render(holder)
    }
    RealtimePreview(rendererState)
}
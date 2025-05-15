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
import com.avin.avinapp.compose.dnd.modifiers.dragTarget
import com.avin.avinapp.compose.dnd.state.rememberDragAndDropState
import com.avin.avinapp.data.models.descriptor.composable.findByDescriptorKey
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.features.editor.component.ProjectEditorComponent
import com.avin.avinapp.features.editor.data.pages.EditorPages
import com.avin.avinapp.features.editor.dsl.titlebar.ProjectEditorTitleBar
import com.avin.avinapp.features.editor.widgets.descriptor_list.ComposableDescriptorList
import com.avin.avinapp.features.editor.widgets.modifiers.ModifiersBar
import com.avin.avinapp.features.editor.widgets.properties.PropertiesBar
import com.avin.avinapp.preview.collector.rememberComponentRenderCollector
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.holder.toHolder
import com.avin.avinapp.preview.realtime.state.rememberRealtimeRenderState
import com.avin.avinapp.preview.realtime.widget.RealtimePreview
import com.avin.avinapp.preview.snapshot.state.rememberSnapshotRenderState
import com.avin.avinapp.preview.snapshot.widgets.SnapshotPreview
import com.avin.avinapp.shortcut.KeyboardKeys
import com.avin.avinapp.shortcut.desktop.DesktopShortcutManager
import com.avin.avinapp.shortcut.handleShortcutManager
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.foundation.window.ApplyWindowMinimumSize
import com.avin.avinapp.utils.compose.modifier.focus.clearFocusWhenPressed
import com.avin.avinapp.utils.compose.nodes.navigation_bar.VerticalNavigationBar
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.Divider

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
    val descriptors by component.descriptors.collectAsState()
    val modifiersDescriptors by component.modifiersDescriptors.collectAsState()
    val editorSettings by component.editorSettings.collectAsState()
    val collector = rememberComponentRenderCollector()
    val dragAndDropState = rememberDragAndDropState()
    val rendererState = rememberSnapshotRenderState(
        devices = devices,
        collector = collector,
        dragAndDropState = dragAndDropState
    )

    LaunchedEffect(descriptors) {
        descriptors.findByDescriptorKey("foundation.column")?.let {
            rendererState.renderPreview(it.toHolder())
        }
    }
    val shortcutManager = remember {
        DesktopShortcutManager().apply {
            getDeleteKey() to {
                rendererState.selectedComponentId?.let {
                    rendererState.removeChild(it)
                    rendererState.renderPreview()
                }
            }
            KeyboardKeys.ESCAPE to { rendererState.clearSelectedComponents() }
        }
    }

    AppCustomWindow(
        onCloseRequest = onCloseRequest, title = projectName, state = rememberWindowState(
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
        Row(
            modifier = Modifier.fillMaxSize()
                .handleShortcutManager(shortcutManager)
        ) {
            VerticalNavigationBar(
                currentPage = currentPage,
                items = remember { EditorPages.navigationPages },
                onPageChanged = component::changePage
            )
            Divider(Orientation.Vertical, modifier = Modifier.fillMaxHeight())
            ComposableDescriptorList(descriptors, dragAndDropState)
            Box(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clearFocusWhenPressed { rendererState.clearSelectedComponents() },
                contentAlignment = Alignment.Center
            ) {
                when (currentPage) {
                    is EditorPages.Screens -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            SnapshotPreview(
                                state = rendererState,
                                collector = collector,
                                dragAndDropState = dragAndDropState,
                                settings = editorSettings.snapshotPreview,
                                modifier = Modifier
                                    .fillMaxHeight(.9f)
                                    .dragTarget(dragAndDropState)
                            )
                        }
                    }

                    else -> {
                        RealtimePreviewSample(
                            rendererState.currentDevice!!,
                            rendererState.lastHolder
                        )
                    }
                }
            }
            Column {
                Box(Modifier.weight(1f)) {
                    PropertiesBar(rendererState)
                }
                Box(Modifier.weight(1f)) {
                    ModifiersBar(rendererState, modifiersDescriptors)
                }
            }
        }
    }
}

@Composable
fun RealtimePreviewSample(device: PreviewDevice, holder: ComposableStateHolder?) {
    val rendererState = rememberRealtimeRenderState(
        device = device,
    )
    LaunchedEffect(Unit) {
        rendererState.render(holder ?: return@LaunchedEffect)
    }
    RealtimePreview(rendererState)
}
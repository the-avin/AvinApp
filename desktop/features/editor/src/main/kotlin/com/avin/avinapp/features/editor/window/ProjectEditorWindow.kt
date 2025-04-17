package com.avin.avinapp.features.editor.window

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.scene.CanvasLayersComposeScene
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.data.models.project.Project
import com.avin.avinapp.device.PreviewDevice
import com.avin.avinapp.features.editor.component.DevicesChooserDropdown
import com.avin.avinapp.features.editor.component.ProjectEditorComponent
import com.avin.avinapp.features.editor.data.pages.EditorPages
import com.avin.avinapp.features.editor.dsl.EditorDropdown
import com.avin.avinapp.features.editor.dsl.EditorTitleBarAction
import com.avin.avinapp.preview.state.rememberPreviewState
import com.avin.avinapp.preview.widgets.ComposablePreview
import com.avin.avinapp.rendering.ComposableRendererImpl
import com.avin.avinapp.theme.AppCustomTheme
import com.avin.avinapp.theme.icon.ColoredIcon
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.utils.getColorForLetter
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.ToggleableIconButton
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls

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
    val rendererState = rememberPreviewState(
        devices = devices,
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
        LaunchedEffect(Unit) {
            rendererState.renderPreview(
                JsonObject(
                    mapOf("type" to JsonPrimitive("Project"))
                )
            )
        }
        Row(modifier = Modifier.fillMaxSize()) {
            Sidebar(
                currentPage = currentPage,
                onPageChanged = component::changePage
            )
            Divider(Orientation.Vertical, modifier = Modifier.fillMaxHeight())
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (currentPage) {
                    is EditorPages.Screens -> {
                        ComposablePreview(
                            state = rendererState,
                            modifier = Modifier.fillMaxHeight(.9f)
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}


@Composable
fun DecoratedWindowScope.ProjectEditorTitleBar(
    projectName: String,
    recentProjects: List<Project>,
    currentDevice: PreviewDevice?,
    devices: List<PreviewDevice>,
    currentPage: EditorPages,
    onOpenProject: (Long) -> Unit,
    onNewProjectClick: () -> Unit,
    onOpenFilePicker: () -> Unit,
    onCloneRepositoryClick: () -> Unit,
    onOpenSettings: () -> Unit,
    onDeviceSelected: (PreviewDevice) -> Unit,
) {
    TitleBar(
        gradientStartColor = getColorForLetter(projectName.firstOrNull() ?: 'A').copy(.6f),
        modifier = Modifier.newFullscreenControls()
    ) {
        EditorDropdown(
            projectName = projectName,
            recentProjects = recentProjects,
            onNewProjectClick = onNewProjectClick,
            onOpenProject = onOpenProject,
            onOpenFilePicker = onOpenFilePicker,
            onCloneRepositoryClick = onCloneRepositoryClick,
        )
        Row(
            modifier = Modifier.align(Alignment.End).padding(end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EditorTitleBarAction(
                onOpenSettings = onOpenSettings,
            )
        }
        if (currentPage is EditorPages.Screens) {
            DevicesChooserDropdown(
                currentDevice = currentDevice,
                devices = devices,
                onDeviceSelected = onDeviceSelected
            )
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
                ColoredIcon(page.icon)
            }
        }
    }
}
package com.avin.avinapp.features.editor.window

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
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
import com.avin.avinapp.features.editor.component.ProjectEditorComponent
import com.avin.avinapp.features.editor.dsl.EditorDropdown
import com.avin.avinapp.features.editor.dsl.EditorTitleBarAction
import com.avin.avinapp.theme.AppCustomTheme
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.utils.getColorForLetter
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text
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
            recentProjects = recentProjects,
            onNewProjectClick = onNewProjectClick,
            onOpenProject = onOpenProject,
            onOpenFilePicker = onOpenFilePicker,
            onCloneRepositoryClick = onCloneRepositoryClick,
            onOpenSettings = onOpenSettings,
        )
        val screenWidth = 1080
        val screenHeight = 2424
        val image = ImageBitmap(screenWidth, screenHeight)
        val canvas = Canvas(image)

        CanvasLayersComposeScene(density = Density(10f), size = IntSize(screenWidth, screenHeight)).apply {
            setContent {
                AppCustomTheme {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Color.Red).padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DefaultButton(onClick = {}) {
                            Text("Hello World", style = TextStyle(color = Color.White))
                        }
                    }
                }
            }
        }.render(canvas, 1)

        Image(bitmap = image, contentDescription = "Rendered Image")
    }
}


@Composable
fun DecoratedWindowScope.ProjectEditorTitleBar(
    projectName: String,
    recentProjects: List<Project>,
    onOpenProject: (Long) -> Unit,
    onNewProjectClick: () -> Unit,
    onOpenFilePicker: () -> Unit,
    onCloneRepositoryClick: () -> Unit,
    onOpenSettings: () -> Unit,
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
        EditorTitleBarAction(
            onOpenSettings = onOpenSettings,
            modifier = Modifier.align(Alignment.End).padding(end = 4.dp)
        )
    }
}
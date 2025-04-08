package com.avin.avinapp.features.editor.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.features.editor.component.ProjectEditorComponent
import com.avin.avinapp.features.editor.dsl.EditorDropdown
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.utils.getColorForLetter
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls

@Composable
fun ProjectEditorWindow(
    component: ProjectEditorComponent,
    onNewProjectClick: () -> Unit,
    onCloneRepositoryClick: () -> Unit,
    onCloseRequest: () -> Unit,
) {
    val project by component.project.collectAsState()
    val projectName = project?.name.orEmpty()
    AppCustomWindow(
        onCloseRequest = onCloseRequest,
        title = projectName,
        state = rememberWindowState(placement = WindowPlacement.Fullscreen)
    ) {
        TitleBar(
            gradientStartColor = getColorForLetter(projectName.firstOrNull() ?: 'A').copy(.6f),
            modifier = Modifier.newFullscreenControls()
        ) {
            EditorDropdown(
                projectName = projectName,
                onNewProjectClick = onNewProjectClick,
                onCloneRepositoryClick = onCloneRepositoryClick,
            )
        }
    }
}
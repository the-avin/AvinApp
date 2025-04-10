package com.avin.avinapp.features.editor.window

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.features.editor.component.ProjectEditorComponent
import com.avin.avinapp.features.editor.dsl.EditorDropdown
import com.avin.avinapp.features.editor.dsl.EditorTitleBarAction
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.menu.buildMenu
import com.avin.avinapp.utils.compose.menu.renderMenu
import com.avin.avinapp.utils.compose.utils.getColorForLetter
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls

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
        state = rememberWindowState(placement = WindowPlacement.Fullscreen)
    ) {
        MenuBar {
            buildMenu {
                +menu(Resource.string.newProject) {
                    +submenu(Resource.string.open) {
                        +item(Resource.string.newProject, action = { println("hello world") })
                        +separator()
                        +item(Resource.string.newProject, action = { println("hello world") })
                    }
                }
            }.also { renderMenu(it) }
        }
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
}
package com.avin.avinapp.features.editor.window

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.features.editor.component.ProjectEditorComponent
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.nodes.text.DrawInitialsWithCanvas
import com.avin.avinapp.utils.compose.utils.getColorForLetter
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls

@Composable
fun ProjectEditorWindow(
    component: ProjectEditorComponent,
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
            Dropdown(menuContent = {
            }, modifier = Modifier.align(Alignment.Start)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    DrawInitialsWithCanvas(projectName, fontScale = .8f, cornerRadius = 4.dp, boxSize = 20.dp)
                    Text(projectName)
                }
            }
        }
    }
}
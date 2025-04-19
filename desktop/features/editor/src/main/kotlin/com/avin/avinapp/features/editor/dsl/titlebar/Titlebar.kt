package com.avin.avinapp.features.editor.dsl.titlebar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.data.models.project.Project
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.features.editor.component.DevicesChooserDropdown
import com.avin.avinapp.features.editor.data.pages.EditorPages
import com.avin.avinapp.utils.compose.utils.getColorForLetter
import org.jetbrains.jewel.window.DecoratedWindowScope
import org.jetbrains.jewel.window.TitleBar
import org.jetbrains.jewel.window.newFullscreenControls

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
//        modifier = Modifier.newFullscreenControls()
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
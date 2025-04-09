package com.avin.avinapp.features.editor.dsl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avin.avinapp.data.models.project.Project
import com.avin.avinapp.data.models.project.valid
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.platform.file.FileHandler
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.utils.compose.foundation.menu.iconTextItem
import com.avin.avinapp.utils.compose.modifier.grayscale
import com.avin.avinapp.utils.compose.nodes.menu.IconMenu
import com.avin.avinapp.utils.compose.nodes.text.DrawInitialsWithCanvas
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.items
import org.jetbrains.jewel.ui.component.separator
import org.jetbrains.jewel.ui.icons.AllIconsKeys
import org.jetbrains.jewel.ui.util.thenIf
import org.jetbrains.jewel.window.TitleBarScope

@Composable
fun TitleBarScope.EditorDropdown(
    projectName: String,
    recentProjects: List<Project>,
    onOpenProject: (Long) -> Unit,
    onNewProjectClick: () -> Unit,
    onOpenFilePicker: () -> Unit,
    onCloneRepositoryClick: () -> Unit,
) {
    Dropdown(menuContent = {
        iconTextItem(
            stringRes = Resource.string.newProject,
            icon = AllIconsKeys.General.Add,
            onClick = onNewProjectClick
        )
        iconTextItem(
            stringRes = Resource.string.open,
            icon = AllIconsKeys.Nodes.Folder,
            onClick = onOpenFilePicker
        )
        iconTextItem(
            stringRes = Resource.string.cloneRepository,
            icon = AllIconsKeys.Vcs.Branch,
            onClick = onCloneRepositoryClick
        )
        if (recentProjects.isNotEmpty()) {
            separator()
            items(
                recentProjects,
                isSelected = { false },
                onItemClick = { if (it.valid) onOpenProject.invoke(it.id) }
            ) { project ->
                val isValid = remember { project.valid }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.thenIf(!isValid) { grayscale() }.padding(vertical = 4.dp),
                ) {
                    DrawInitialsWithCanvas(project.name, boxSize = 20.dp)
                    Column {
                        Text(project.name)
                        Text(project.path, fontSize = 10.sp, modifier = Modifier.alpha(.6f))
                    }
                }
            }
        }
    }, modifier = Modifier.align(Alignment.Start)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            DrawInitialsWithCanvas(projectName, fontScale = .8f, cornerRadius = 4.dp, boxSize = 20.dp)
            Text(projectName)
        }
    }
}
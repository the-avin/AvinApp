package com.avin.avinapp.features.projects.page

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avin.avinapp.data.models.project.Project
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.platform.file.FileHandler
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.utils.compose.nodes.menu.IconMenu
import com.avin.avinapp.utils.compose.nodes.text.DrawInitialsWithCanvas
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.theme.colorPalette

@Composable
fun ProjectsList(
    projects: List<Project>, onDeleteProject: (Project) -> Unit,
    onOpenProject: (Long) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)) {
        items(projects, key = { it.id }) { project ->
            ProjectItem(
                project = project,
                onDelete = { onDeleteProject(project) },
                onOpenProject = { onOpenProject.invoke(project.id) })
        }
    }
}

@Composable
fun ProjectItem(
    project: Project, onDelete: () -> Unit,
    onOpenProject: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    Box(
        Modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .background(
                if (isHovered) JewelTheme.colorPalette.blue[5].copy(.3f) else Color.Transparent
            )
            .pointerHoverIcon(PointerIcon.Hand)
            .hoverable(interactionSource)
            .pointerInput(Unit) {
                detectTapGestures {
                    onOpenProject.invoke()
                }
            }
            .padding(8.dp)

    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            DrawInitialsWithCanvas(project.name)
            Column {
                Text(project.name)
                Text(project.path, fontSize = 12.sp)
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                IconMenu {
                    selectableItem(false, onClick = {
                        FileHandler.openFolder(project.path)
                    }) {
                        Text(dynamicStringRes(Resource.string.openInFolder))
                    }
                    selectableItem(false, onClick = onDelete) {
                        Text(dynamicStringRes(Resource.string.deleteProjectFromList))
                    }
                }
            }
        }
    }
}
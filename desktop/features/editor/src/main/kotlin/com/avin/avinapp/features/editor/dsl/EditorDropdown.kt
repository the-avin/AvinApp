package com.avin.avinapp.features.editor.dsl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.utils.compose.foundation.menu.iconTextItem
import com.avin.avinapp.utils.compose.nodes.text.DrawInitialsWithCanvas
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.icons.AllIconsKeys
import org.jetbrains.jewel.window.TitleBarScope

@Composable
fun TitleBarScope.EditorDropdown(
    projectName: String,
    onNewProjectClick: () -> Unit,
    onCloneRepositoryClick: () -> Unit,
) {
    Dropdown(menuContent = {
        iconTextItem(
            stringRes = Resource.string.newProject,
            icon = AllIconsKeys.General.Add,
            onClick = onNewProjectClick
        )
        iconTextItem(
            stringRes = Resource.string.cloneRepository,
            icon = AllIconsKeys.Vcs.Branch,
            onClick = onCloneRepositoryClick
        )
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
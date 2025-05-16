package com.avin.avinapp.features.projects.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.features.projects.component.ProjectsComponent
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.buttons.SecondaryButton
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.foundation.window.ApplyWindowMinimumSize
import com.avin.avinapp.utils.compose.modifier.allPadding
import com.avin.avinapp.utils.compose.modifier.horizontalPadding
import com.avin.avinapp.utils.compose.modifier.topPadding
import com.avin.avinapp.utils.compose.modifier.windowBackground
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.IndeterminateHorizontalProgressBar
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField
import org.jetbrains.jewel.ui.icons.AllIconsKeys

@Composable
fun ProjectsWindow(
    component: ProjectsComponent,
    onCloseRequest: () -> Unit,
    onOpenSettings: () -> Unit,
    onNewProjectClick: () -> Unit,
    onOpenCloneRepository: () -> Unit,
    onOpenFilePicker: () -> Unit,
    onOpenProject: (Long) -> Unit,
) {
    val projects by component.projects.collectAsState(emptyList())
    val loading by component.loading.collectAsState()
    AppCustomWindow(
        onCloseRequest = onCloseRequest,
        title = dynamicStringRes(Resource.string.welcome),
        state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
        )
    ) {
        window.ApplyWindowMinimumSize()
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Column(
                modifier = Modifier.fillMaxSize().windowBackground().horizontalPadding()
                    .topPadding()
            ) {
                Header(
                    onSearchValueChange = component::search,
                    onNewProjectClick = onNewProjectClick,
                    onOpenFilePicker = onOpenFilePicker,
                    onOpenCloneRepository = onOpenCloneRepository
                )
                if (loading) {
                    IndeterminateHorizontalProgressBar(Modifier.fillMaxWidth().topPadding())
                } else {
                    Divider(
                        Orientation.Horizontal,
                        modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
                    )
                }
                ProjectsList(
                    projects = projects,
                    onDeleteProject = component::deleteProject,
                    onOpenProject = onOpenProject
                )
            }
            IconButton(onClick = onOpenSettings, modifier = Modifier.allPadding()) {
                Icon(
                    key = AllIconsKeys.General.Settings,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun Header(
    onSearchValueChange: (String) -> Unit,
    onNewProjectClick: () -> Unit,
    onOpenFilePicker: () -> Unit,
    onOpenCloneRepository: () -> Unit,
) {
    val searchState = rememberTextFieldState()
    LaunchedEffect(Unit) {
        snapshotFlow { searchState.text }.collectLatest {
            onSearchValueChange.invoke(it.toString())
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            state = searchState,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search") },
        )
        SecondaryButton(onClick = onNewProjectClick) {
            Text(dynamicStringRes(Resource.string.newProject))
        }
        SecondaryButton(onClick = onOpenFilePicker) {
            Text(dynamicStringRes(Resource.string.open))
        }
        SecondaryButton(onClick = onOpenCloneRepository) {
            Text(dynamicStringRes(Resource.string.cloneRepository))
        }
    }
}
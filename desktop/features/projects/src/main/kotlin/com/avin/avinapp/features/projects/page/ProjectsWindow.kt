package com.avin.avinapp.features.projects.page

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.features.projects.component.ProjectsComponent
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.buttons.SecondaryButton
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.modifier.allPadding
import com.avin.avinapp.utils.compose.modifier.horizontalPadding
import com.avin.avinapp.utils.compose.modifier.topPadding
import com.avin.avinapp.utils.compose.modifier.windowBackground
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.IndeterminateHorizontalProgressBar
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun ProjectsWindow(
    component: ProjectsComponent,
    onCloseRequest: () -> Unit,
    onNewProjectClick: () -> Unit,
) {
    val projects by component.projects.collectAsState(emptyList())
    val searchValue by component.searchValue.collectAsState()
    val loading by component.loading.collectAsState()
    AppCustomWindow(onCloseRequest = onCloseRequest, title = dynamicStringRes(Resource.string.welcome)) {
        Column(modifier = Modifier.fillMaxSize().windowBackground().horizontalPadding().topPadding()) {
            Header(
                searchValue = searchValue,
                onSearchValueChange = component::search,
                onNewProjectClick = onNewProjectClick
            )
            if (loading) {
                IndeterminateHorizontalProgressBar(Modifier.fillMaxWidth().topPadding())
            } else {
                Divider(Orientation.Horizontal, modifier = Modifier.padding(top = 8.dp).fillMaxWidth())
            }
            ProjectsList(projects = projects)
        }
    }
}

@Composable
fun Header(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onNewProjectClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchValue,
            onValueChange = onSearchValueChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search") },
        )
        SecondaryButton(onClick = onNewProjectClick) {
            Text(dynamicStringRes(Resource.string.newProject))
        }
        SecondaryButton(onClick = {}) {
            Text(dynamicStringRes(Resource.string.open))
        }
        SecondaryButton(onClick = {}) {
            Text(dynamicStringRes(Resource.string.cloneRepository))
        }
    }
}
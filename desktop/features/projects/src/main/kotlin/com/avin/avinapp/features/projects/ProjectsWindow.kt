package com.avin.avinapp.features.projects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.features.projects.component.ProjectsComponent
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.buttons.SecondaryButton
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.allPadding
import com.avin.avinapp.utils.compose.topPadding
import com.avin.avinapp.utils.compose.windowBackground
import org.jetbrains.jewel.ui.component.IndeterminateHorizontalProgressBar
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun ProjectsWindow(
    component: ProjectsComponent,
    onCloseRequest: () -> Unit,
) {
    AppCustomWindow(onCloseRequest = onCloseRequest, title = dynamicStringRes(Resource.string.welcome)) {
        Column(modifier = Modifier.fillMaxSize().windowBackground().allPadding()) {
            Header(
                searchState = component.searchState
            )
            IndeterminateHorizontalProgressBar(modifier = Modifier.fillMaxWidth().topPadding())
        }
    }
}

@Composable
fun Header(
    searchState: TextFieldState
) {
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
        SecondaryButton(onClick = {}) {
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
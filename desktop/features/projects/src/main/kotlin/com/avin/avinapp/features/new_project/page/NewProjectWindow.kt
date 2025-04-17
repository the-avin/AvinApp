package com.avin.avinapp.features.new_project.page

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.core.data.state.new_project.NewProjectStatus
import com.avin.avinapp.core.data.state.new_project.onError
import com.avin.avinapp.core.data.state.new_project.onLoading
import com.avin.avinapp.core.data.state.new_project.onSuccess
import com.avin.avinapp.features.data.state.NewProjectState
import com.avin.avinapp.features.new_project.component.NewProjectComponent
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.foundation.layout.endWithCustomSpace
import com.avin.avinapp.utils.compose.foundation.window.ApplyWindowMinimumSize
import com.avin.avinapp.utils.compose.modifier.bottomPadding
import com.avin.avinapp.utils.compose.modifier.horizontalPadding
import com.avin.avinapp.utils.compose.modifier.topPadding
import com.avin.avinapp.utils.compose.modifier.windowBackground
import com.avin.avinapp.utils.compose.nodes.field.RowFolderPickerField
import com.avin.avinapp.utils.compose.nodes.field.RowTextField
import com.avin.avinapp.utils.compose.nodes.text.WarningMessage
import com.avin.avinapp.utils.compose.window.loading.LoadingDialog
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.*

@Composable
fun NewProjectWindow(
    component: NewProjectComponent,
    onCloseRequest: () -> Unit
) {
    val title = dynamicStringRes(Resource.string.newProject)
    val scrollState = rememberScrollState()
    val state by component.state.collectAsState()
    val createStatus by component.status.collectAsState()
    val projectAlreadyExistsError = component.projectAlreadyExistsError
    val fieldsEmptyError = component.fieldsEmptyError
    AppCustomWindow(
        onCloseRequest = onCloseRequest,
        title = title,
        state = rememberWindowState(width = 700.dp, height = 500.dp, position = WindowPosition(Alignment.Center)),
    ) {
        window.ApplyWindowMinimumSize()
        Column(
            Modifier.windowBackground().fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .topPadding()
                    .horizontalPadding()
                    .horizontalPadding(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(dynamicStringRes(Resource.string.newProjectMessage), modifier = Modifier.padding(top = 4.dp))
                }
                Spacer(Modifier.height(16.dp))
                RowTextField(
                    value = state.name,
                    onValueChange = { component.updateState(state.copy(name = it)) },
                    label = dynamicStringRes(Resource.string.name),
                )
                RowFolderPickerField(
                    path = state.path,
                    onPathChange = { component.updateState(state.copy(path = it)) },
                    onPickFromPicker = {
                        component.pickedFromPicker = true
                    },
                    label = dynamicStringRes(Resource.string.path),
                )
                Spacer(Modifier.height(12.dp))
                CheckboxRow(
                    checked = state.addToGit,
                    onCheckedChange = { component.updateState(state.copy(addToGit = it)) },
                ) {
                    Text(dynamicStringRes(Resource.string.addToGit))
                }

            }
            ErrorsSection(
                projectAlreadyExistsError,
                fieldsEmptyError,
                state
            )
            Divider(Orientation.Horizontal, modifier = Modifier.fillMaxWidth())
            Row(
                modifier = Modifier.fillMaxWidth().bottomPadding().horizontalPadding(),
                horizontalArrangement = Arrangement.endWithCustomSpace(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = onCloseRequest) {
                    Text(text = dynamicStringRes(Resource.string.cancel))
                }
                DefaultButton(onClick = {
                    component.createProject()
                }, enabled = projectAlreadyExistsError.not() && fieldsEmptyError.not()) {
                    Text(text = dynamicStringRes(Resource.string.finish))
                }
            }
        }
    }
    createStatus.onLoading {
        LoadingDialog(
            currentMessage = createStatus.getMessage(),
            title = dynamicStringRes(Resource.string.creatingNewProject),
        )
    }.onSuccess {
        LaunchedEffect(Unit) { onCloseRequest() }
    }.onError { }
}


@Composable
fun NewProjectStatus.getMessage(): String {
    return when (this) {
        NewProjectStatus.Creating -> dynamicStringRes(Resource.string.creatingFiles)
        NewProjectStatus.AddGit -> dynamicStringRes(Resource.string.addingToGit)
        is NewProjectStatus.Error, is NewProjectStatus.Idle, is NewProjectStatus.Completed -> ""
    }
}


@Composable
fun ErrorsSection(projectAlreadyExistsError: Boolean, fieldsEmptyError: Boolean, state: NewProjectState) {
    Column {
        AnimatedVisibility(
            visible = projectAlreadyExistsError,
            enter = expandVertically(expandFrom = Alignment.CenterVertically) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically) + fadeOut()
        ) {
            WarningMessage(
                message = dynamicStringRes(Resource.string.existsProjectMessage, mapOf("path" to state.path)),
                modifier = Modifier.horizontalPadding()
            )
        }
        AnimatedVisibility(
            visible = fieldsEmptyError,
            enter = expandVertically(expandFrom = Alignment.CenterVertically) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically) + fadeOut()
        ) {
            WarningMessage(
                message = dynamicStringRes(Resource.string.emptyFieldsMessage),
                modifier = Modifier.horizontalPadding().padding(top = 8.dp)
            )
        }
    }
}
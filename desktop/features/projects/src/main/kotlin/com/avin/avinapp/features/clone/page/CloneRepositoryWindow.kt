package com.avin.avinapp.features.clone.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.avin.avinapp.features.clone.component.CloneRepositoryComponent
import com.avin.avinapp.features.data.status.onLoading
import com.avin.avinapp.features.data.status.onSuccess
import com.avin.avinapp.manager.compose.dynamicStringRes
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.window.AppCustomWindow
import com.avin.avinapp.utils.compose.foundation.layout.endWithCustomSpace
import com.avin.avinapp.utils.compose.modifier.bottomPadding
import com.avin.avinapp.utils.compose.modifier.horizontalPadding
import com.avin.avinapp.utils.compose.modifier.topPadding
import com.avin.avinapp.utils.compose.modifier.windowBackground
import com.avin.avinapp.utils.compose.nodes.field.RowFolderPickerField
import com.avin.avinapp.utils.compose.nodes.field.RowTextField
import com.avin.avinapp.utils.compose.nodes.text.WarningMessage
import com.avin.avinapp.utils.compose.window.loading.LoadingDialog
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.OutlinedButton
import org.jetbrains.jewel.ui.component.Text
import java.io.File

@Composable
fun CloneRepositoryWindow(
    component: CloneRepositoryComponent,
    onCloseRequest: () -> Unit
) {
    val title = dynamicStringRes(Resource.string.cloneRepository)
    val state by component.state.collectAsState()
    val status by component.status.collectAsState()
    val scrollState = rememberScrollState()
    val isFilesExists = remember(state.path) {
        File(state.path).listFiles()?.isNotEmpty() == true
    }
    AppCustomWindow(
        onCloseRequest = onCloseRequest, title = title,
        state = rememberWindowState(width = 700.dp, height = 500.dp, position = WindowPosition(Alignment.Center)),
    ) {
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
                    Text(dynamicStringRes(Resource.string.cloneProjectMessage), modifier = Modifier.padding(top = 4.dp))
                }
                Spacer(Modifier.height(16.dp))
                RowTextField(
                    value = state.url,
                    onValueChange = { component.updateState(state.copy(url = it)) },
                    label = dynamicStringRes(Resource.string.url),
                )
                RowFolderPickerField(
                    path = state.path,
                    onPathChange = { component.updateState(state.copy(path = it)) },
                    onPickFromPicker = {
                        component.wasSetPathManual = true
                    },
                    label = dynamicStringRes(Resource.string.path),
                )
            }
            AnimatedVisibility(
                visible = isFilesExists,
                enter = expandVertically(expandFrom = Alignment.CenterVertically) + fadeIn(),
                exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically) + fadeOut()
            ) {
                WarningMessage(
                    message = dynamicStringRes(Resource.string.existsFileMessage, mapOf("path" to state.path)),
                    modifier = Modifier.horizontalPadding()
                )
            }
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
                    component.cloneProject()
                }, enabled = !isFilesExists) {
                    Text(text = dynamicStringRes(Resource.string.finish))
                }
            }
        }
        status.onSuccess {
            LaunchedEffect(Unit) { onCloseRequest.invoke() }
        }.onLoading {
            LoadingDialog(
                title = title,
                currentMessage = "${it.title} (${it.currentWork} / ${it.totalWorks})"
            )
        }
    }
}
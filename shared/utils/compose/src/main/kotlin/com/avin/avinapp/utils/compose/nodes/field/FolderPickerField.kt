package com.avin.avinapp.utils.compose.nodes.field

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.icon.ColoredIcon
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.compose.rememberDirectoryPickerLauncher
import io.github.vinceglb.filekit.path
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun FolderPickerField(
    path: String,
    onPathChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val folderPicker = rememberDirectoryPickerLauncher(
        directory = path.takeIf { it.isNotEmpty() }?.let {
            remember { PlatformFile(it) }
        }
    ) {
        if (it?.path?.isNotEmpty() == true) {
            onPathChange.invoke(it.path)
        }
    }
    TextField(
        value = path,
        onValueChange = onPathChange,
        trailingIcon = {
            IconButton(onClick = {
                folderPicker.launch()
            }) {
                ColoredIcon(
                    Resource.image.FOLDER
                )
            }
        },
        modifier = modifier
    )
}
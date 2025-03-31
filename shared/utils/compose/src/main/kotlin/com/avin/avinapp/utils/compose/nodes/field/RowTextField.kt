package com.avin.avinapp.utils.compose.nodes.field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun RowTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            label,
            modifier = Modifier.width(120.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RowFolderPickerField(
    path: String,
    onPathChange: (String) -> Unit,
    label: String
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            label,
            modifier = Modifier.width(120.dp)
        )
        FolderPickerField(
            path = path,
            onPathChange = onPathChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
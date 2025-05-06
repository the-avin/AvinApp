package com.avin.avinapp.features.editor.widgets.properties.items

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun ParameterTextItem(
    initialValue: String?,
    onUpdateValue: (String) -> Unit
) {
    val state = rememberTextFieldState(initialValue.orEmpty())

    LaunchedEffect(Unit) {
        snapshotFlow { state.text }.collectLatest {
            onUpdateValue.invoke(it.toString())
        }
    }

    TextField(
        state = state,
        modifier = Modifier.fillMaxWidth()
    )
}
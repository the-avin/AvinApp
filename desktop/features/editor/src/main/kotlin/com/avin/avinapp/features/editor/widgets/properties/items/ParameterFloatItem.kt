package com.avin.avinapp.features.editor.widgets.properties.items

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avin.avinapp.utils.compose.nodes.field.ExpressionTextField

@Composable
fun ParameterFloatItem(
    initialValue: Float?,
    onUpdateValue: (Float) -> Unit
) {
    ExpressionTextField(
        value = initialValue ?: 0f,
        onValueChanged = onUpdateValue,
        modifier = Modifier.fillMaxWidth()
    )
}
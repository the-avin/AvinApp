package com.avin.avinapp.features.editor.widgets.properties.items.float

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.jewel.ui.component.Slider

@Composable
fun ParameterFloatSliderItem(
    initialValue: Float?,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>? = null
) {
    var currentValue by remember {
        mutableFloatStateOf(initialValue ?: 0f)
    }
    Slider(
        currentValue,
        onValueChange = {
            onValueChange.invoke(it)
            currentValue = it
        },
        modifier = Modifier.fillMaxWidth(),
        valueRange = valueRange ?: 0f..1f
    )
}
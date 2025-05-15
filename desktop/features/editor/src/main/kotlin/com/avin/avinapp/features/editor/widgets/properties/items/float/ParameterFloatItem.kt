package com.avin.avinapp.features.editor.widgets.properties.items.float

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avin.avinapp.data.domain.parameter.FieldType
import com.avin.avinapp.data.domain.parameter.ParameterType
import com.avin.avinapp.data.extensions.parameter.valueRange
import com.avin.avinapp.utils.compose.nodes.field.ExpressionTextField
import org.jetbrains.jewel.ui.component.Slider

@Composable
fun ParameterFloatItem(
    initialValue: Float?,
    type: ParameterType.FloatType,
    onUpdateValue: (Float) -> Unit
) {
    when (type.fieldType) {
        FieldType.INPUT -> {
            ExpressionTextField(
                value = initialValue ?: 0f,
                onValueChanged = onUpdateValue,
                modifier = Modifier.fillMaxWidth()
            )
        }

        FieldType.SLIDER -> {
            ParameterFloatSliderItem(
                initialValue = initialValue ?: 0f,
                onValueChange = onUpdateValue,
                valueRange = type.valueRange
            )
        }
    }
}
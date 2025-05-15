package com.avin.avinapp.features.editor.widgets.properties

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avin.avinapp.data.domain.parameter.ParameterType
import com.avin.avinapp.data.models.descriptor.parameter.ParameterDescriptor
import com.avin.avinapp.features.editor.widgets.properties.items.ParameterFloatItem
import com.avin.avinapp.features.editor.widgets.properties.items.ParameterIntItem
import com.avin.avinapp.features.editor.widgets.properties.items.ParameterTextItem
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ParameterItem(
    initialValue: Any?,
    parameter: ParameterDescriptor,
    onUpdateValue: (Any?) -> Unit
) {
    Column {
        Text(
            text = parameter.name,
            modifier = Modifier.padding(start = 4.dp, bottom = 3.dp),
            fontSize = 12.sp
        )
        when (parameter.type) {
            ParameterType.StringType -> {
                ParameterTextItem(initialValue?.toString(), onUpdateValue)
            }

            ParameterType.IntType -> {
                ParameterIntItem(initialValue as? Int, onUpdateValue)
            }

            ParameterType.FloatType -> {
                ParameterFloatItem(initialValue as? Float, onUpdateValue)
            }

            else -> error("Unsupported parameter")
        }
    }
}
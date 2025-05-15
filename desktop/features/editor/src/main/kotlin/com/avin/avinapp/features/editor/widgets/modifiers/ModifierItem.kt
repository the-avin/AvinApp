package com.avin.avinapp.features.editor.widgets.modifiers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.avin.avinapp.data.models.descriptor.modifier.ModifierDescriptor
import com.avin.avinapp.data.models.modifier.ModifierValues
import com.avin.avinapp.features.editor.widgets.properties.ParameterItem
import com.avin.avinapp.preview.extensions.typedDefaultValue
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ModifierItem(
    modifier: ModifierDescriptor,
    values: ModifierValues,
    onNewRenderRequest: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(modifier.name)
        modifier.parameters.forEach { parameter ->
            val initialValue = values[parameter.descriptorKey] ?: parameter.defaultValue?.let {
                remember { parameter.typedDefaultValue }
            }
            ParameterItem(
                initialValue = initialValue,
                parameter = parameter,
                onUpdateValue = {
                    values[parameter.descriptorKey] = it
                    onNewRenderRequest.invoke()
                }
            )
        }
    }
}
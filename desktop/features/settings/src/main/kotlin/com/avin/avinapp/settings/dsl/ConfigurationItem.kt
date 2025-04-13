package com.avin.avinapp.settings.dsl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import com.avin.avinapp.data.models.settings.config.SettingsConfiguration
import com.avin.avinapp.data.models.settings.config.SettingsType
import org.jetbrains.jewel.ui.component.Checkbox
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ConfigurationItem(
    configuration: SettingsConfiguration<*>,
    onValueChange: (Any) -> Unit
) {
    when (configuration.type) {
        is SettingsType.Checkbox -> {
            val value by configuration.initialValues.collectAsState(null)
            val boolValue = (value ?: configuration.defaultValue.invoke()) as Boolean
            Checkbox(
                checked = boolValue,
                onCheckedChange = { onValueChange(it) }
            )
        }

        else -> {
            Text("Unsupported setting type")
        }
    }
}
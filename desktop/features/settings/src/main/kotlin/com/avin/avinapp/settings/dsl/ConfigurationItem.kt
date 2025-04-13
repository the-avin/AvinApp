package com.avin.avinapp.settings.dsl

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.data.models.settings.config.SettingsConfiguration
import com.avin.avinapp.data.models.settings.config.SettingsType
import com.avin.avinapp.manager.compose.dynamicStringRes
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.*
import org.jetbrains.jewel.ui.theme.simpleListItemStyle

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

        is SettingsType.Enum -> {
            ListComboBox(
                items = (configuration.type as SettingsType.Enum).values.map { dynamicStringRes(it) },
                listItemContent = { item, isSelected, _, isItemHovered, isPreviewSelection ->
                    SimpleListItem(
                        text = item,
                        state = ListItemState(isSelected, isItemHovered, isPreviewSelection),
                        style = JewelTheme.simpleListItemStyle,
                        contentDescription = item
                    )
                },
                isEditable = false,
                onSelectedItemChange = { onValueChange(it) },
                modifier = Modifier.width(120.dp),
                maxPopupHeight = 100.dp
            )
        }

        else -> {
            Text("Unsupported setting type")
        }
    }
}
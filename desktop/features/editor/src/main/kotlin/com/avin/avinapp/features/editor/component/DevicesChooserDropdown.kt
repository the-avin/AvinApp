package com.avin.avinapp.features.editor.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.resource.Resource
import com.avin.avinapp.theme.icon.ColoredIcon
import org.jetbrains.jewel.foundation.theme.LocalContentColor
import org.jetbrains.jewel.ui.component.Dropdown
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.items
import org.jetbrains.jewel.ui.icons.AllIconsKeys

@Composable
fun DevicesChooserDropdown(
    currentDevice: PreviewDevice?,
    devices: List<PreviewDevice>,
    onDeviceSelected: (PreviewDevice) -> Unit,
) {
    Dropdown(
        menuContent = {
            items(devices, isSelected = { currentDevice == it }, onItemClick = onDeviceSelected) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (currentDevice == it) {
                        Icon(
                            key = AllIconsKeys.General.InspectionsOK,
                            contentDescription = null,
                            tint = LocalContentColor.current
                        )
                    }
                    Text(it.name)
                }
            }
        }
    ) {
        currentDevice?.let {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ColoredIcon(
                    Resource.image.MOBILE
                )
                Text(it.name)
            }
        }
    }
}
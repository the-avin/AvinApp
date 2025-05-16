package com.avin.avinapp.color_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ReusableContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.avin.avinapp.compose.eyedropper.rememberEyedropperState
import com.avin.avinapp.utils.compose.nodes.popup.PopupContainer
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.IconButton
import org.jetbrains.jewel.ui.icons.AllIconsKeys

@Composable
fun ColorPickerPopup(
    initialColor: Color? = null,
    onDismissRequest: () -> Unit
) {
    val eyedropperState = rememberEyedropperState()
    var color by remember {
        mutableStateOf(HsvColor.from(initialColor ?: Color.Red))
    }
    var hoveredColor: Color? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        eyedropperState.onColorHovered {
            hoveredColor = it
        }
        eyedropperState.onColorPicked {
            color = HsvColor.from(it)
        }
    }
    DisposableEffect(Unit) {
        onDispose { eyedropperState.stop() }
    }
    PopupContainer(
        onDismissRequest = onDismissRequest,
        popupProperties = PopupProperties(
            focusable = true,
            dismissOnClickOutside = eyedropperState.isPicking.not()
        ),
    ) {
        Column(
            Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.End
        ) {
            ReusableContent(color) {
                ClassicColorPicker(
                    color = color,
                    onColorChanged = {},
                    modifier = Modifier.size(300.dp, 150.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (eyedropperState.isPicking && hoveredColor != null) {
                    Box(Modifier.size(40.dp).background(hoveredColor!!))
                }
                IconButton(onClick = {
                    eyedropperState.start()
                }, enabled = eyedropperState.isPicking.not()) {
                    Icon(
                        AllIconsKeys.Ide.Pipette,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
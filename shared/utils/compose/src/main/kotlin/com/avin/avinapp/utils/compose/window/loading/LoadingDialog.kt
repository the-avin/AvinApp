package com.avin.avinapp.utils.compose.window.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.DialogWindowScope
import androidx.compose.ui.window.rememberDialogState
import com.avin.avinapp.utils.compose.modifier.allPadding
import com.avin.avinapp.utils.compose.modifier.windowBackground
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.IndeterminateHorizontalProgressBar
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.window.DecoratedWindowScope
import java.awt.Color
import javax.print.attribute.standard.Sides

@Composable
fun LoadingDialog(
    title: String,
    onCloseRequest: () -> Unit = {},
    currentMessage: String? = null
) {
    DialogWindow(
        onCloseRequest = onCloseRequest,
        state = rememberDialogState(
            width = 300.dp,
            height = 84.dp
        ),
        title = title,
        alwaysOnTop = true
    ) {
        ApplyWindowColors()
        Box(Modifier.fillMaxSize().windowBackground().allPadding(), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                currentMessage?.let {
                    Text(it)
                }
                IndeterminateHorizontalProgressBar(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}


@Composable
fun DialogWindowScope.ApplyWindowColors() {
    val panelColor = JewelTheme.globalColors.panelBackground
    SideEffect {
        panelColor.run {
            window.background = Color(red, green, blue, alpha)
        }
    }
}
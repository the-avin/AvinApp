package com.avin.avinapp.settings.dsl

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.avin.avinapp.data.models.settings.page.SettingsPage
import com.avin.avinapp.manager.compose.dynamicStringRes
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.theme.colorPalette

@Composable
fun Sidebar(
    currentPage: SettingsPage?,
    pages: List<SettingsPage>,
    onPageChange: (SettingsPage) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(.3f), contentPadding = PaddingValues(16.dp)) {
        items(pages, key = { it.name.resId }) { page ->
            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()
            Box(
                Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .fillMaxWidth()
                    .background(
                        when {
                            currentPage?.name == page.name -> JewelTheme.colorPalette.blue[3]
                            isHovered -> JewelTheme.colorPalette.blue[5].copy(.3f)
                            else -> Color.Transparent
                        }
                    )
                    .hoverable(interactionSource)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onPageChange.invoke(page)
                        }
                    }
                    .pointerHoverIcon(PointerIcon.Hand)
                    .padding(8.dp)

            ) {
                Text(dynamicStringRes(page.name))
            }
        }
    }
}
package com.avin.avinapp.compose.eyedropper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.flow.collectLatest

object EyedropperDefaults {
    @Composable
    fun MagnifierOverlay(
        state: EyedropperState,
        scale: Int = 5
    ) = MagnifierOverlayImpl(state, scale)
}

@Composable
private fun MagnifierOverlayImpl(
    state: EyedropperState,
    scale: Int = 5
) {
    val image = state.magnifierImage
    val position = state.mousePosition

    if (image == null || state.isPicking.not()) return

    val imgBitmap = remember(image) {
        image.toComposeImageBitmap()
    }

    val imageWidth = image.width * scale
    val imageHeight = image.height * scale

    val windowState = rememberWindowState(
        position = WindowPosition(
            position.x.dp - imageWidth.dp / 2,
            (position.y - 100).dp
        ),
        width = imageWidth.dp,
        height = imageHeight.dp
    )

    LaunchedEffect(Unit) {
        snapshotFlow { state.mousePosition }.collectLatest { pos ->
            windowState.position = WindowPosition(
                (pos.x - (image.width * scale) / 2).dp,
                (pos.y - 100).dp
            )
        }
    }

    Window(
        onCloseRequest = {},
        undecorated = true,
        transparent = true,
        resizable = false,
        focusable = false,
        state = windowState,
        alwaysOnTop = true
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .border(2.dp, Color.Black, CircleShape)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = imgBitmap,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                filterQuality = FilterQuality.None
            )
            Box(
                Modifier
                    .size(3.dp)
                    .background(Color.Black, shape = CircleShape)
            )
        }
    }
}

package com.avin.avinapp.preview.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.avin.avinapp.preview.state.PreviewState
import org.jetbrains.jewel.ui.component.CircularProgressIndicator

@Composable
fun ComposablePreview(
    state: PreviewState,
    modifier: Modifier = Modifier
) {
    if (state.currentImage != null) {
        Box(modifier.background(Color.White), contentAlignment = Alignment.Center) {
            Image(
                bitmap = state.currentImage!!,
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(),
                contentScale = ContentScale.FillHeight
            )
            if (state.isRendering) {
                CircularProgressIndicator()
            }
        }
    }
}
package com.avin.avinapp.preview.renderer

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import com.avin.avinapp.data.models.device.PreviewDevice
import kotlinx.serialization.json.JsonObject

interface ComposableRenderer {
    fun renderComposable(json: JsonObject): @Composable () -> Unit
    suspend fun renderImage(json: JsonObject, device: PreviewDevice): ImageBitmap

    companion object {
        const val TYPE = "type"
        const val CHILDREN = "children"

        const val RENDER_NANO_TIME = 66_000_000L // (66ms ~ 15fps)
    }
}
package com.avin.avinapp.preview.renderer

import androidx.compose.runtime.Composable
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.scene.ComposeScene
import com.avin.avinapp.data.models.device.PreviewDevice
import kotlinx.serialization.json.JsonObject
import org.jetbrains.skia.Bitmap

interface ComposableRenderer {
    fun renderComposable(json: JsonObject): @Composable () -> Unit

    @OptIn(InternalComposeUiApi::class)
    suspend fun renderImage(json: JsonObject, device: PreviewDevice): ImageBitmap

    @OptIn(InternalComposeUiApi::class)
    fun getScene(device: PreviewDevice): ComposeScene

    @OptIn(InternalComposeUiApi::class)
    suspend fun ComposeScene.render(device: PreviewDevice): ImageBitmap

    companion object {
        const val TYPE = "type"
        const val CHILDREN = "children"

        const val RENDER_NANO_TIME = 66_000_000L // (66ms ~ 15fps)
    }
}
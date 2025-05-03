package com.avin.avinapp.preview.renderer

import androidx.compose.runtime.Composable
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.scene.ComposeScene
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.preview.holder.ComposableStateHolder
import kotlinx.serialization.json.JsonObject
import org.jetbrains.skia.Bitmap

interface ComposableRenderer {
    fun renderComposable(holder: ComposableStateHolder): @Composable () -> Unit

    @OptIn(InternalComposeUiApi::class)
    suspend fun renderImage(holder: ComposableStateHolder, device: PreviewDevice): ImageBitmap

    @OptIn(InternalComposeUiApi::class)
    fun getScene(device: PreviewDevice): ComposeScene

    @OptIn(InternalComposeUiApi::class)
    suspend fun ComposeScene.render(device: PreviewDevice): ImageBitmap

    companion object {
        const val RENDER_NANO_TIME = 66_000_000L // (66ms ~ 15fps)
    }
}
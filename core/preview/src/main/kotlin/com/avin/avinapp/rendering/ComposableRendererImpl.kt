package com.avin.avinapp.rendering

import androidx.compose.runtime.Composable
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.scene.CanvasLayersComposeScene
import androidx.compose.ui.scene.ComposeScene
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import com.avin.avinapp.device.PreviewDevice
import com.avin.avinapp.theme.AppCustomTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.Text

class ComposableRendererImpl : ComposableRenderer {
    override fun renderComposable(json: JsonObject): @Composable () -> Unit {
        val type = json[ComposableRenderer.TYPE]?.jsonPrimitive?.contentOrNull ?: "Unknown"
        return {
            DefaultButton(onClick = {}) {
                Text(type)
            }
        }
    }

    @OptIn(InternalComposeUiApi::class)
    override suspend fun renderImage(json: JsonObject, device: PreviewDevice) = withContext(Dispatchers.Default) {
        val image = ImageBitmap(device.resolution.width, device.resolution.height)
        val canvas = Canvas(image)

        getScene(device).apply {
            setContent {
                AppCustomTheme {
                    renderComposable(json).invoke()
                }
            }
        }.render(canvas, ComposableRenderer.RENDER_NANO_TIME)

        image
    }

    @OptIn(InternalComposeUiApi::class)
    private fun getScene(device: PreviewDevice): ComposeScene =
        CanvasLayersComposeScene(
            density = Density(device.density),
            size = IntSize(device.resolution.width, device.resolution.height)
        )
}

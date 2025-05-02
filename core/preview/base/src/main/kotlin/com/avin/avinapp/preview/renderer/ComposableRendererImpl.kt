package com.avin.avinapp.preview.renderer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.scene.CanvasLayersComposeScene
import androidx.compose.ui.scene.ComposeScene
import androidx.compose.ui.unit.Density
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.data.models.device.intSize
import com.avin.avinapp.preview.collector.ComponentRenderCollector
import com.avin.avinapp.preview.collector.NewLocalComponentRenderCollector
import com.avin.avinapp.preview.collector.trackRender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive


class ComposableRendererImpl(
    private val collector: ComponentRenderCollector,
) : ComposableRenderer {
    override fun renderComposable(json: JsonObject): @Composable () -> Unit {
        val count = json[ComposableRenderer.TYPE]?.jsonPrimitive?.contentOrNull?.toInt() ?: 1
        return {
            NewLocalComponentRenderCollector(collector) {
                MaterialTheme {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = {}, modifier = Modifier.trackRender("B1")) {
                            Text("This is a test")
                        }
                    }
                }
            }
        }
    }

    @OptIn(InternalComposeUiApi::class)
    override suspend fun renderImage(json: JsonObject, device: PreviewDevice): ImageBitmap {
        val bitmap: ImageBitmap
        getScene(device).apply {
            setContent {
                renderComposable(json).invoke()
            }
            bitmap = render(device)
            close()
        }
        return bitmap
    }

    @OptIn(InternalComposeUiApi::class)
    override fun getScene(device: PreviewDevice): ComposeScene =
        CanvasLayersComposeScene(
            density = Density(device.density),
            size = device.resolution.intSize,
        )

    @OptIn(InternalComposeUiApi::class)
    override suspend fun ComposeScene.render(device: PreviewDevice): ImageBitmap =
        withContext(Dispatchers.Default) {
            val image = createBitmapForDevice(device)
            val canvas = Canvas(image)
            render(canvas, ComposableRenderer.RENDER_NANO_TIME)
            image
        }


    private fun createBitmapForDevice(device: PreviewDevice) =
        ImageBitmap(device.resolution.width.toInt(), device.resolution.height.toInt())
}


@Composable
fun rememberComposableRenderer(collector: ComponentRenderCollector) =
    remember { ComposableRendererImpl(collector) }
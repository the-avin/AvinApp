package com.avin.avinapp.rendering

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.scene.CanvasLayersComposeScene
import androidx.compose.ui.scene.ComposeScene
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import com.avin.avinapp.collector.ComponentRenderCollector
import com.avin.avinapp.collector.NewLocalComponentRenderCollector
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.data.models.widget.buttonDescriptor
import com.avin.avinapp.runtime.InvokeComposableService
import com.avin.avinapp.runtime.InvokeComposableServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive


class ComposableRendererImpl(
    private val collector: ComponentRenderCollector,
    val invokeComposableService: InvokeComposableService = InvokeComposableServiceImpl()
) : ComposableRenderer {
    override fun renderComposable(json: JsonObject): @Composable () -> Unit {
        val count = json[ComposableRenderer.TYPE]?.jsonPrimitive?.contentOrNull?.toInt() ?: 1
        return {
            NewLocalComponentRenderCollector(collector) {
                Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    repeat(count) {
                        invokeComposableService.invokeCaching(
                            it.toString(),
                            currentComposer,
                            buttonDescriptor
                        )
                    }
                }
            }
        }
    }

    @OptIn(InternalComposeUiApi::class, ExperimentalComposeUiApi::class)
    override suspend fun renderImage(json: JsonObject, device: PreviewDevice) =
        withContext(Dispatchers.Default) {
            val image = ImageBitmap(device.resolution.width, device.resolution.height)
            val canvas = Canvas(image)

            getScene(device).apply {
                setContent {
                    MaterialTheme {
                        renderComposable(json).invoke()
                    }
                }
                render(canvas, ComposableRenderer.RENDER_NANO_TIME)
                close()
            }

            image
        }

    @OptIn(InternalComposeUiApi::class, ExperimentalComposeUiApi::class)
    private fun getScene(device: PreviewDevice): ComposeScene =
        CanvasLayersComposeScene(
            density = Density(device.density),
            size = IntSize(device.resolution.width, device.resolution.height)
        )
}


@Composable
fun rememberComposableRenderer(collector: ComponentRenderCollector) =
    remember { ComposableRendererImpl(collector) }
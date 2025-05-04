package com.avin.avinapp.preview.renderer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
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
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.registry.ComposableRegistry
import com.avin.avinapp.preview.registry.ProvideComposableRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ComposableRendererImpl(
    private val collector: ComponentRenderCollector,
    private val registry: ComposableRegistry
) : ComposableRenderer {
    override fun renderComposable(holder: ComposableStateHolder): @Composable (() -> Unit) {
        return {
            NewLocalComponentRenderCollector(collector) {
                ProvideComposableRegistry(
                    registry = registry
                ) {
                    MaterialTheme {
                        Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            registry.renderComposable(holder)
                        }
                    }
                }
            }
        }
    }

    @OptIn(InternalComposeUiApi::class)
    override suspend fun renderImage(
        holder: ComposableStateHolder,
        device: PreviewDevice
    ): ImageBitmap {
        val bitmap: ImageBitmap
        getScene(device).apply {
            setContent {
                renderComposable(holder).invoke()
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
fun rememberComposableRenderer(collector: ComponentRenderCollector, registry: ComposableRegistry) =
    remember { ComposableRendererImpl(collector, registry) }
package com.avin.avinapp.preview.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.scene.CanvasLayersComposeScene
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.preview.collector.ComponentRenderCollector
import com.avin.avinapp.preview.renderer.ComposableRenderer
import com.avin.avinapp.preview.renderer.rememberComposableRenderer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeout

@Stable
@InternalComposeUiApi
class RealtimeRendererState(
    val device: PreviewDevice,
    private val scope: CoroutineScope,
    private val renderer: ComposableRenderer
) {
    internal var currentImage by mutableStateOf<ImageBitmap?>(null)
        private set
    private val mutex = Mutex()

    private val scene by lazy {
        CanvasLayersComposeScene(
            density = Density(device.density),
            size = IntSize(device.resolution.width, device.resolution.height),
            invalidate = ::realtimeInvalidate,
            coroutineContext = Dispatchers.Default
        )
    }

    internal var componentSize by mutableStateOf(Size.Zero)

    @Volatile
    private var renderJob: Job? = null

    init {
        initialContent()
    }

    fun sendPointerEvent(type: PointerEventType, position: Offset) {
        scene.sendPointerEvent(
            eventType = type,
            position = position
        )
    }

    private var lastRealtimeJob: Job? = null

    private fun realtimeInvalidate() {
        lastRealtimeJob?.cancel()
        lastRealtimeJob = scope.launch {
            while (true) {
                ensureActive()
                invalidate()
                withFrameNanos { }
            }
        }
    }

    private fun initialContent() {
        scene.setContent {
            MaterialTheme {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = {}) {

                    }
                }
            }
        }
    }

    fun invalidate() {
        renderJob?.let {
            if (it.isActive) return
        }
        if (scene.hasInvalidations().not()) return

        renderJob = scope.launch(scope.coroutineContext) {
            runCatching {
                mutex.withLock {
                    withTimeout(500) {
                        currentImage = renderer.run { scene.render(device = device) }
                    }
                }
            }
        }
    }
}

@InternalComposeUiApi
@Composable
fun rememberRealtimeRenderState(
    device: PreviewDevice,
    collector: ComponentRenderCollector,
    renderer: ComposableRenderer = rememberComposableRenderer(collector)
): RealtimeRendererState {
    val scope = rememberCoroutineScope()
    val state = remember {
        RealtimeRendererState(
            device = device,
            scope = scope,
            renderer = renderer,
        )
    }
    return state
}
package com.avin.avinapp.preview.state

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import com.avin.avinapp.collector.ComponentRenderCollector
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.logger.AppLogger
import com.avin.avinapp.rendering.ComposableRenderer
import com.avin.avinapp.rendering.rememberComposableRenderer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.JsonObject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.system.measureTimeMillis

@Stable
class PreviewState(
    private val devices: List<PreviewDevice>,
    initialDevice: PreviewDevice? = devices.firstOrNull(),
    private val scope: CoroutineScope,
    private val renderer: ComposableRenderer
) {
    var currentDevice by mutableStateOf(initialDevice)
        private set

    internal var currentImage by mutableStateOf<ImageBitmap?>(null)
        private set

    private val mutex = Mutex()
    private var lastObject: JsonObject? = null

    var isRendering by mutableStateOf(false)
        private set

    internal var componentSize = Size.Zero

    @Volatile
    private var renderJob: Job? = null

    init {
        invalidate()
    }

    fun selectDevice(device: PreviewDevice) {
        if (device == currentDevice) return
        currentDevice = device
        invalidate()
    }

    fun invalidate() {
        val json = lastObject ?: return
        val device = currentDevice ?: return

        renderJob?.cancel()
        renderJob = scope.launch(scope.coroutineContext) {
            isRendering = true
            try {
                mutex.withLock {
                    val renderTime = measureTimeMillis {
                        val newImage = renderer.renderImage(json, device)
                        currentImage = newImage
                    }
                    AppLogger.debug(LOG_TAG, "Rendering took $renderTime ms for ${device.name}")
                }
            } catch (_: CancellationException) {
            } catch (_: Exception) {
            } finally {
                isRendering = false
            }
        }
    }

    fun renderPreview(json: JsonObject) {
        lastObject = json
        if (currentDevice == null) return
        invalidate()
    }


    companion object {
        private const val LOG_TAG = "PreviewState"
    }
}

@Composable
fun rememberPreviewState(
    devices: List<PreviewDevice>,
    collector: ComponentRenderCollector,
    initialDevice: PreviewDevice? = devices.firstOrNull(),
    renderer: ComposableRenderer = rememberComposableRenderer(collector)
): PreviewState {
    val scope = rememberCoroutineScope()
    val state = remember {
        PreviewState(
            devices = devices,
            initialDevice = initialDevice,
            scope = scope,
            renderer = renderer,
        )
    }
    LaunchedEffect(devices) {
        if (state.currentDevice == null && devices.isNotEmpty()) {
            state.selectDevice(devices.first())
        }
    }
    return state
}
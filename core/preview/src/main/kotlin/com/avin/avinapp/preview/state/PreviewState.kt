package com.avin.avinapp.preview.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import com.avin.avinapp.device.PreviewDevice
import com.avin.avinapp.rendering.ComposableRenderer
import com.avin.avinapp.rendering.ComposableRendererImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlin.coroutines.cancellation.CancellationException

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
        renderJob = scope.launch {
            isRendering = true
            try {
                mutex.withLock {
                    currentImage = renderer.renderImage(json, device)
                }
            } catch (_: CancellationException) {
            } catch (_: Exception) {
            } finally {
                isRendering = false
            }
        }
    }

    suspend fun renderPreview(json: JsonObject) {
        lastObject = json
        if (currentDevice == null) return
        withContext(scope.coroutineContext) {
            invalidate()
        }
    }
}

@Composable
fun rememberPreviewState(
    devices: List<PreviewDevice>,
    initialDevice: PreviewDevice? = devices.firstOrNull(),
    renderer: ComposableRenderer = ComposableRendererImpl()
): PreviewState {
    val scope = rememberCoroutineScope()
    val state = remember {
        PreviewState(
            devices = devices, initialDevice = initialDevice, scope = scope, renderer = renderer
        )
    }
    LaunchedEffect(devices) {
        if (state.currentDevice == null && devices.isNotEmpty()) {
            state.selectDevice(devices.first())
        }
    }
    return state
}
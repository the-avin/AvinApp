package com.avin.avinapp.preview.snapshot.state

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import com.avin.avinapp.preview.collector.ComponentRenderCollector
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.logger.AppLogger
import com.avin.avinapp.preview.data.models.RenderedComponentInfo
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.providers.registry.rememberDefaultComposableRegistry
import com.avin.avinapp.preview.registry.ComposableRegistry
import com.avin.avinapp.preview.renderer.ComposableRenderer
import com.avin.avinapp.preview.renderer.rememberComposableRenderer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.cancellation.CancellationException
import kotlin.system.measureTimeMillis

@Stable
class SnapshotRenderState(
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
    private var lastHolder: ComposableStateHolder? = null

    var selectedComponent by mutableStateOf<RenderedComponentInfo?>(null)
        private set

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
        scope.launch {
            mutex.withLock {
                renderJob?.cancel()
                renderJob = launch {
                    isRendering = true
                    try {
                        val renderTime = measureTimeMillis {
                            val newImage = renderer.renderImage(lastHolder!!, currentDevice!!)
                            currentImage = newImage
                        }
                        AppLogger.info(
                            LOG_TAG,
                            "Rendering took $renderTime ms for ${currentDevice!!.name}"
                        )
                    } catch (_: CancellationException) {
                    } catch (_: Exception) {
                    } finally {
                        isRendering = false
                    }
                }
            }
        }
    }

    fun renderPreview(holder: ComposableStateHolder) {
        lastHolder = holder
        if (currentDevice == null) return
        invalidate()
    }

    fun selectComponent(component: RenderedComponentInfo) {
        selectedComponent = component
    }

    fun clearSelectedComponents() {
        selectedComponent = null
    }

    companion object {
        private const val LOG_TAG = "PreviewState"
    }
}

@Composable
fun rememberSnapshotRenderState(
    devices: List<PreviewDevice>,
    collector: ComponentRenderCollector,
    initialDevice: PreviewDevice? = devices.firstOrNull(),
    registry: ComposableRegistry = rememberDefaultComposableRegistry(),
    renderer: ComposableRenderer = rememberComposableRenderer(collector, registry)
): SnapshotRenderState {
    val scope = rememberCoroutineScope()
    val state = remember {
        SnapshotRenderState(
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
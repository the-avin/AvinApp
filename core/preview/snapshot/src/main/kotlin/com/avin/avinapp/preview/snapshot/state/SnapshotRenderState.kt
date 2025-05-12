package com.avin.avinapp.preview.snapshot.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import com.avin.avinapp.compose.dnd.state.DragAndDropState
import com.avin.avinapp.data.models.descriptor.composable.ComposableDescriptor
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.data.models.device.size
import com.avin.avinapp.logger.AppLogger
import com.avin.avinapp.preview.collector.ComponentRenderCollector
import com.avin.avinapp.preview.data.models.RenderedComponentInfo
import com.avin.avinapp.preview.data.models.findTopmostParentComponentByPosition
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.holder.toHolder
import com.avin.avinapp.preview.providers.registry.rememberDefaultComposableRegistry
import com.avin.avinapp.preview.providers.registry.rememberDefaultModifierRegistry
import com.avin.avinapp.preview.renderer.ComposableRenderer
import com.avin.avinapp.preview.renderer.rememberComposableRenderer
import com.avin.avinapp.preview.snapshot.utils.mapPointerToDevice
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
    private val collector: ComponentRenderCollector,
    private val renderer: ComposableRenderer,
    private val dragAndDropState: DragAndDropState
) {
    var currentDevice by mutableStateOf(initialDevice)
        private set

    var selectedComponentId by mutableStateOf<String?>(null)
        private set

    var isRendering by mutableStateOf(false)
        private set

    var lastHolder: ComposableStateHolder? by mutableStateOf(null)

    internal var currentImage by mutableStateOf<ImageBitmap?>(null)
        private set

    internal var componentSize by mutableStateOf(Size.Zero)

    private val mutex = Mutex()

    @Volatile
    private var renderJob: Job? = null

    init {
        initDragAndDrop()
    }

    private fun initDragAndDrop() {
        dragAndDropState.onDroppedWithType<ComposableDescriptor> { offset, descriptor ->
            currentDevice ?: return@onDroppedWithType
            val mapped =
                mapPointerToDevice(offset, componentSize, currentDevice!!.resolution.size)
            collector.components.findTopmostParentComponentByPosition(mapped)?.let { info ->
                addChild(info.id, descriptor.toHolder())
                renderPreview()
            }
        }
    }

    fun selectDevice(device: PreviewDevice) {
        if (device != currentDevice) {
            currentDevice = device
            invalidate()
        }
    }

    fun renderPreview(holder: ComposableStateHolder? = lastHolder) {
        lastHolder = holder
        if (currentDevice != null) {
            invalidate()
        }
    }

    fun selectComponent(component: RenderedComponentInfo) {
        selectedComponentId = component.id
    }

    fun clearSelectedComponents() {
        selectedComponentId = null
    }

    private fun invalidate() {
        scope.launch {
            mutex.withLock {
                renderJob?.cancel()
                renderJob = launch {
                    isRendering = true
                    try {
                        val renderTime = measureTimeMillis {
                            val image = renderer.renderImage(
                                lastHolder ?: return@measureTimeMillis,
                                currentDevice ?: return@measureTimeMillis
                            )
                            currentImage = image
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

    fun addChild(componentId: String, holder: ComposableStateHolder) {
        lastHolder
            ?.findHolderById(componentId)
            ?.addChild(holder)
    }

    fun removeChild(componentId: String) {
        if (selectedComponentId == componentId) clearSelectedComponents()
        lastHolder
            ?.removeChildById(componentId)
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
    renderer: ComposableRenderer = rememberComposableRenderer(
        collector = collector,
        registry = rememberDefaultComposableRegistry(),
        modifierRegistry = rememberDefaultModifierRegistry()
    ),
    dragAndDropState: DragAndDropState
): SnapshotRenderState {
    val scope = rememberCoroutineScope()
    val state = remember {
        SnapshotRenderState(
            devices = devices,
            initialDevice = initialDevice,
            scope = scope,
            renderer = renderer,
            collector = collector,
            dragAndDropState = dragAndDropState
        )
    }
    LaunchedEffect(devices) {
        if (state.currentDevice == null && devices.isNotEmpty()) {
            state.selectDevice(devices.first())
        }
    }
    return state
}
package com.avin.avinapp.preview.realtime.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.avin.avinapp.data.models.device.PreviewDevice
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.providers.registry.rememberDefaultComposableRegistry
import com.avin.avinapp.preview.registry.ComposableRegistry
import com.avin.avinapp.preview.renderer.ComposableRenderer
import com.avin.avinapp.preview.renderer.rememberComposableRenderer

@Stable
class RealtimeRendererState(
    initialDevice: PreviewDevice,
    private val renderer: ComposableRenderer
) {
    var currentContent by mutableStateOf<(@Composable () -> Unit)?>(null)
    var currentDevice by mutableStateOf(initialDevice)


    fun render(holder: ComposableStateHolder) {
        currentContent = renderer.renderComposable(holder)
    }
}

@Composable
fun rememberRealtimeRenderState(
    device: PreviewDevice,
    registry: ComposableRegistry = rememberDefaultComposableRegistry(),
    renderer: ComposableRenderer = rememberComposableRenderer(
        collector = null, registry = registry
    )
): RealtimeRendererState {
    val state = remember {
        RealtimeRendererState(
            initialDevice = device,
            renderer = renderer
        )
    }
    LaunchedEffect(device) {
        state.currentDevice = device
    }
    return state
}
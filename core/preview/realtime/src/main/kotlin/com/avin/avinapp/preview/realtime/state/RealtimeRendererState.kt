package com.avin.avinapp.preview.realtime.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.avin.avinapp.data.models.device.PreviewDevice

@Stable
class RealtimeRendererState(
    initialDevice: PreviewDevice,
) {
    var currentContent by mutableStateOf<(@Composable () -> Unit)?>(null)
    var currentDevice by mutableStateOf(initialDevice)

    init {
        initialContent()
    }

    private fun initialContent() {
        currentContent = {
            MaterialTheme {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = {}) {
                        Text("This is a test")
                    }
                }
            }
        }
    }
}

@Composable
fun rememberRealtimeRenderState(
    device: PreviewDevice,
): RealtimeRendererState {
    val state = remember {
        RealtimeRendererState(
            initialDevice = device,
        )
    }
    LaunchedEffect(device) {
        state.currentDevice = device
    }
    return state
}
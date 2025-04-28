package com.avin.avinapp.preview.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import com.avin.avinapp.data.models.device.PreviewDevice

@Stable
class RealtimeRendererState(
    val device: PreviewDevice,
) {
    var currentContent by mutableStateOf<(@Composable () -> Unit)?>(null)

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

    fun invalidate() {

    }
}

@Composable
fun rememberRealtimeRenderState(
    device: PreviewDevice,
): RealtimeRendererState {
    val state = remember {
        RealtimeRendererState(
            device = device,
        )
    }
    return state
}
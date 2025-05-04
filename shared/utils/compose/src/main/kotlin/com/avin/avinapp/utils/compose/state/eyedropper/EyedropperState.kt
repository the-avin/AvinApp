package com.avin.avinapp.utils.compose.state.eyedropper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.awt.MouseInfo
import java.awt.Robot
import java.awt.event.MouseEvent


/**
 * A class for picking colors from the screen using the mouse pointer.
 */
class EyedropperState(
    private val scope: CoroutineScope
) {
    private var job: Job? = null
    private val robot = Robot()

    private var onColorPicked: (Color) -> Unit = {}

    fun start() {
        job?.cancel()

        job = scope.launch {
            try {
                java.awt.Toolkit.getDefaultToolkit().addAWTEventListener(
                    { event ->
                        if (event is MouseEvent && event.id == MouseEvent.MOUSE_PRESSED) {
                            val location = MouseInfo.getPointerInfo().location
                            val awtColor = robot.getPixelColor(location.x, location.y)
                            val color = Color(awtColor.red, awtColor.green, awtColor.blue)
                            stop()
                            onColorPicked(color)
                        }
                    },
                    java.awt.AWTEvent.MOUSE_EVENT_MASK
                )
            } catch (_: Exception) {
                stop()
            }
        }
    }

    fun onColorPicked(action: (Color) -> Unit) {
        onColorPicked = action
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}

@Composable
fun rememberEyedropperState(): EyedropperState {
    val scope = rememberCoroutineScope()
    val state = remember { EyedropperState(scope) }
    DisposableEffect(Unit) {
        onDispose { state.stop() }
    }
    return state
}
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
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent
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
    private var onColorHovered: (Color) -> Unit = {}

    private val toolkit by lazy {
        java.awt.Toolkit.getDefaultToolkit()
    }

    private val awtEventListener by lazy {
        AWTEventListener { event ->
            when (event) {
                is MouseEvent -> {
                    val location = MouseInfo.getPointerInfo().location
                    val awtColor = robot.getPixelColor(location.x, location.y)
                    val color = Color(awtColor.red, awtColor.green, awtColor.blue)
                    onColorHovered.invoke(color)

                    if (event.id == MouseEvent.MOUSE_PRESSED) {
                        stop()
                        onColorPicked.invoke(color)
                    }
                }

                is KeyEvent -> {
                    if (event.keyCode == KeyEvent.VK_ESCAPE) {
                        stop()
                    }
                }
            }
        }
    }

    fun start() {
        job?.cancel()

        job = scope.launch {
            try {
                // Add both mouse and keyboard event masks
                toolkit.addAWTEventListener(
                    awtEventListener,
                    java.awt.AWTEvent.MOUSE_EVENT_MASK or java.awt.AWTEvent.KEY_EVENT_MASK
                )
            } catch (_: Exception) {
                stop()
            }
        }
    }

    fun onColorPicked(action: (Color) -> Unit) {
        onColorPicked = action
    }

    fun onColorHovered(action: (Color) -> Unit) {
        onColorHovered = action
    }

    fun stop() {
        job?.cancel()
        job = null
        toolkit.removeAWTEventListener(awtEventListener)
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
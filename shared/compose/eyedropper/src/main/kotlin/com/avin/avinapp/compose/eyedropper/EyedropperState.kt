package com.avin.avinapp.compose.eyedropper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.AWTEvent
import java.awt.MouseInfo
import java.awt.Robot
import java.awt.Toolkit
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent


/**
 * A class for picking colors from the screen using the mouse pointer.
 */
@Stable
class EyedropperState(
    private val scope: CoroutineScope
) {
    private var job: Job? = null
    private val robot = Robot()

    private var onColorPicked: (Color) -> Unit = {}
    private var onColorHovered: (Color) -> Unit = {}

    var isPicking by mutableStateOf(false)
        private set

    private val toolkit by lazy {
        Toolkit.getDefaultToolkit()
    }

    private val awtEventListener by lazy {
        AWTEventListener { event ->
            when (event) {
                is MouseEvent -> {
                    val location = MouseInfo.getPointerInfo().location
                    val awtColor = robot.getPixelColor(location.x, location.y)
                    val color = Color(awtColor.red, awtColor.green, awtColor.blue)
                    onColorHovered.invoke(color)
                    println(color)

                    if (event.id == MouseEvent.MOUSE_PRESSED) {
                        onColorPicked.invoke(color)
                        stop()
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
        if (isPicking) return

        job?.cancel()
        job = scope.launch {
            try {
                toolkit.addAWTEventListener(
                    awtEventListener,
                    AWTEvent.MOUSE_EVENT_MASK or
                            AWTEvent.KEY_EVENT_MASK or
                            AWTEvent.MOUSE_MOTION_EVENT_MASK
                )
                isPicking = true
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
        isPicking = false
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
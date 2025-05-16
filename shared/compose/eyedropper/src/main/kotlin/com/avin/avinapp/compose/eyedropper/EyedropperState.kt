package com.avin.avinapp.compose.eyedropper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.AWTEvent
import java.awt.MouseInfo
import java.awt.Point
import java.awt.Rectangle
import java.awt.Robot
import java.awt.Toolkit
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage


/**
 * A class for picking colors from the screen using the mouse pointer.
 */
@Stable
class EyedropperState(
    private val takeScreenshot: Boolean = false,
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

    var mousePosition by mutableStateOf(IntOffset(0, 0))
        private set

    var magnifierImage by mutableStateOf<BufferedImage?>(null)
        private set


    private val awtEventListener by lazy {
        AWTEventListener { event ->
            when (event) {
                is MouseEvent -> {
                    val location = MouseInfo.getPointerInfo().location
                    takeScreenshot(location)
                    val awtColor = robot.getPixelColor(location.x, location.y)
                    val color = Color(awtColor.red, awtColor.green, awtColor.blue)
                    onColorHovered.invoke(color)

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

    private fun takeScreenshot(location: Point) {
        if (takeScreenshot.not()) return
        mousePosition = IntOffset(location.x, location.y)

        val screenshotSize = 15
        val screenshot = robot.createScreenCapture(
            Rectangle(
                location.x - screenshotSize / 2,
                location.y - screenshotSize / 2,
                screenshotSize,
                screenshotSize
            )
        )
        magnifierImage = screenshot
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
fun rememberEyedropperState(takeScreenshot: Boolean = false): EyedropperState {
    val scope = rememberCoroutineScope()
    val state = remember { EyedropperState(takeScreenshot, scope) }
    DisposableEffect(Unit) {
        onDispose { state.stop() }
    }
    return state
}
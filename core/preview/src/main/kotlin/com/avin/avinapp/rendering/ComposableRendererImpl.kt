package com.avin.avinapp.rendering

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.scene.CanvasLayersComposeScene
import androidx.compose.ui.scene.ComposeScene
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.avin.avinapp.collector.ComponentRenderCollector
import com.avin.avinapp.collector.NewLocalComponentRenderCollector
import com.avin.avinapp.data.models.widget.buttonDescriptor
import com.avin.avinapp.device.PreviewDevice
import com.avin.avinapp.runtime.InvokeComposableService
import com.avin.avinapp.runtime.InvokeComposableServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive


//@Composable
//fun ReflectMaterial3Button() {
//    val composer = currentComposer
//
//    val clazz = Class.forName("androidx.compose.material3.ButtonKt")
//    val method = clazz.methods.firstOrNull {
//        it.name == "Button" && it.parameterTypes.size == 13
//    } ?: error("Button function not found")
//
//    val onClick: () -> Unit = { println("Button clicked!") }
//
//    val modifier = Modifier
//    val enabled = true
//    val shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
//    val colors = androidx.compose.material3.ButtonDefaults.buttonColors()
//    val elevation = androidx.compose.material3.ButtonDefaults.buttonElevation()
//    val border: BorderStroke? = null
//    val contentPadding = androidx.compose.material3.ButtonDefaults.ContentPadding
//    val interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource()
//
//    // مهم: این composable lambda باید توی همون scope اجرا بشه
//    val content: @Composable RowScope.() -> Unit = {
//        androidx.compose.material3.Text("Button from Reflection")
//    }
//
//    // حالا callش کنیم:
//    method.invoke(
//        null, // چون تابع static هست
//        onClick,
//        modifier,
//        enabled,
//        shape,
//        colors,
//        elevation,
//        border,
//        contentPadding,
//        interactionSource,
//        content,
//        composer,
//        1, // changed
//    )
//}


class ComposableRendererImpl(
    private val collector: ComponentRenderCollector,
    val invokeComposableService: InvokeComposableService = InvokeComposableServiceImpl()
) : ComposableRenderer {
    override fun renderComposable(json: JsonObject): @Composable () -> Unit {
        val type = json[ComposableRenderer.TYPE]?.jsonPrimitive?.contentOrNull ?: "Unknown"
        return {
            NewLocalComponentRenderCollector(collector) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Button(onClick = {}, modifier = Modifier.trackRender("test")) {
//                        Text(type)
//                    }
                    invokeComposableService.invoke(
                        currentComposer,
                        buttonDescriptor
                    )
                    runCatching {
                    }.onFailure {
                        println(it.message)
                    }
                }
            }
        }
    }

    @OptIn(InternalComposeUiApi::class, ExperimentalComposeUiApi::class)
    override suspend fun renderImage(json: JsonObject, device: PreviewDevice) =
        withContext(Dispatchers.Default) {
            val image = ImageBitmap(device.resolution.width, device.resolution.height)
            val canvas = Canvas(image)

            getScene(device).apply {
                setContent {
                    MaterialTheme {
                        renderComposable(json).invoke()
                    }
                }
            }.render(canvas, ComposableRenderer.RENDER_NANO_TIME)

            image
        }

    @OptIn(InternalComposeUiApi::class)
    private fun getScene(device: PreviewDevice): ComposeScene =
        CanvasLayersComposeScene(
            density = Density(device.density),
            size = IntSize(device.resolution.width, device.resolution.height)
        )
}


@Composable
fun rememberComposableRenderer(collector: ComponentRenderCollector) =
    remember { ComposableRendererImpl(collector) }
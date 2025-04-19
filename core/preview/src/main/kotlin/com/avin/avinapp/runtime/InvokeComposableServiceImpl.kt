package com.avin.avinapp.runtime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.collector.trackRender
import com.avin.avinapp.data.models.widget.ComposableDescriptor
import java.lang.reflect.Method

class InvokeComposableServiceImpl : InvokeComposableService {
    var listener: InvokeComposableServiceListener? = null

    @Composable
    override fun invoke(
        composer: Composer,
        descriptor: ComposableDescriptor,
    ) {
        val method = findMethod(
            targetClass = descriptor.targetClass,
            functionName = descriptor.functionName,
//            argsSize = descriptor.arguments.size
            argsSize = 10
        ) ?: error("Function not found: ${descriptor.functionName}")
        val onClick: () -> Unit = { println("Button clicked!") }

        val modifier = Modifier.trackRender("1")
        val enabled = true
        val shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
        val colors = androidx.compose.material3.ButtonDefaults.buttonColors()
        val elevation = androidx.compose.material3.ButtonDefaults.buttonElevation()
        val border: BorderStroke? = null
        val contentPadding = androidx.compose.material3.ButtonDefaults.ContentPadding
        val interactionSource = androidx.compose.foundation.interaction.MutableInteractionSource()

        val content: @Composable RowScope.() -> Unit = {
            androidx.compose.material3.Text("Button from Reflection")
        }

        method.invoke(
            null,
            *listOf(
                onClick,
                modifier,
                enabled,
                shape,
                colors,
                elevation,
                border,
                contentPadding,
                interactionSource,
                content,
            ).getFullArgs(composer).toTypedArray()
        )
    }

    @Composable
    override fun invokeCaching(
        composer: Composer,
        descriptor: ComposableDescriptor
    ) {
        runCatching {
            invoke(composer, descriptor)
        }.onFailure {
            it.printStackTrace()
            listener?.onError(it)
        }
    }


    private fun findMethod(targetClass: String, functionName: String, argsSize: Int): Method? {
        val clazz = Class.forName(targetClass)
        return clazz.methods?.find {
            it.name == functionName
                    && it.parameterTypes.size == argsSize + 3
                    && it.parameterTypes[argsSize] == Composer::class.java
        }
    }

    private fun List<Any?>.getFullArgs(composer: Composer): List<Any?> {
        return toList() + composer + CHANGED_FLAG + DEFAULT_FLAG
    }

    companion object {
        private const val CHANGED_FLAG = 1
        private const val DEFAULT_FLAG = 0
    }
}
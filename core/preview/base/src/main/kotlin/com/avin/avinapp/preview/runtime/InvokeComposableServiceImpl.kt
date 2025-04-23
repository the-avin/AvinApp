package com.avin.avinapp.preview.runtime

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avin.avinapp.data.models.widget.ComposableDescriptor
import com.avin.avinapp.logger.AppLogger
import com.avin.avinapp.preview.collector.trackRender
import com.avin.avinapp.preview.data.mapper.toRuntimeValue
import java.lang.reflect.Field
import java.lang.reflect.Method

class InvokeComposableServiceImpl : InvokeComposableService {
    var listener: InvokeComposableServiceListener? = null


    @Composable
    override fun invoke(id: String, composer: Composer, descriptor: ComposableDescriptor) {
        val method = findComposeMethod(
            targetClass = descriptor.targetClass,
            functionName = descriptor.functionName,
            argsSize = descriptor.arguments.size
        ) ?: error("Function not found: ${descriptor.functionName}")

        val onClick: () -> Unit = { println("Button clicked!") }

        val modifier = Modifier.trackRender(id)
        val enabled = true
        val shape = RoundedCornerShape(8.dp)
        val colors = ButtonDefaults.buttonColors()
        val elevation = ButtonDefaults.buttonElevation()
        val border: BorderStroke? = null
        val contentPadding = ButtonDefaults.ContentPadding
        val interactionSource = MutableInteractionSource()

        val content: @Composable RowScope.() -> Unit = {
            Text("Button from Reflection")
        }
        val arguments = processArguments(descriptor.arguments, composer)
        println(arguments)
        method.invokeComposable(composer, arguments)
//        method.invokeComposable(
//            composer,
//            listOf(
//                onClick,
//                modifier,
//                enabled,
//                shape,
//                colors,
//                elevation,
//                border,
//                contentPadding,
//                interactionSource,
//                content,
//            )

//        )
    }


    @Composable
    override fun invokeCaching(
        id: String,
        composer: Composer,
        descriptor: ComposableDescriptor,
    ) {
        runCatching {
            invoke(id, composer, descriptor)
        }.onFailure {
            AppLogger.error(LOG_TAG, it)
            listener?.onError(it)
        }
    }

    @Composable
    private fun processArguments(
        arguments: List<ComposableDescriptor.Argument>,
        composer: Composer
    ): List<Any?> {
        return arguments.map { argument ->
            when {
                argument.defaultValue == null -> null

                else -> {
                    processArgumentValue(
                        argument = argument,
                        argumentValue = argument.defaultValue!!,
                        composer = composer
                    )
                }
            }
        }
    }

    @Composable
    private fun processArgumentValue(
        argument: ComposableDescriptor.Argument,
        argumentValue: ComposableDescriptor.Argument.ArgumentValue,
        composer: Composer
    ): Any? {
        println(argument.name)
        return when (argumentValue) {
            is ComposableDescriptor.Argument.ArgumentValue.Primitive -> {
                argumentValue.toRuntimeValue(argument.type)
            }

            is ComposableDescriptor.Argument.ArgumentValue.Composable -> {
                if (argumentValue.descriptor == null) return {} as (@Composable RowScope.() -> Unit)
                getDefaultValueForMethod(
                    className = argumentValue.descriptor!!.targetClass,
                    methodName = argumentValue.descriptor!!.functionName,
                    composer = composer,
                    parameterCount = argumentValue.argumentSize
                )
            }

            is ComposableDescriptor.Argument.ArgumentValue.Modifier -> Modifier
            is ComposableDescriptor.Argument.ArgumentValue.Lambda -> {
                {} as () -> Unit
            }

            is ComposableDescriptor.Argument.ArgumentValue.Variable -> {
                findVariable(
                    targetClass = argumentValue.targetClass,
                    variableName = argumentValue.variableName,
                )?.get(null)
            }

            else -> null
        }
    }


    private fun findVariable(
        targetClass: String,
        variableName: String
    ): Field? {
        val clazz = Class.forName(targetClass)
        return clazz.fields.find { it.name == variableName }
    }

    private fun findComposeMethod(
        targetClass: String,
        functionName: String,
        argsSize: Int
    ): Method? {
        val clazz = Class.forName(targetClass)
        return clazz.methods?.find {
            it.name == functionName
                    && it.parameterTypes.size == argsSize + 3
                    && it.parameterTypes[argsSize] == Composer::class.java
        }
    }

    @Throws(IllegalArgumentException::class)
    @Composable
    fun getDefaultValueForMethod(
        className: String,
        methodName: String,
        parameterCount: Int,
        composer: Composer
    ): Any? {
        val clazz = Class.forName(className)
        val method = clazz.methods.find {
            it.name == methodName && it.parameterTypes.size == parameterCount + 2
        }
        method?.isAccessible = true
        println(
            "${method?.name} ${method?.parameterTypes?.toList()}"
        )
        val args = Array<Any?>(parameterCount + 2) { null }
        args[parameterCount] = composer
        args[parameterCount + 1] = CHANGED_FLAG


        return method?.invoke(null, *args).also { println(it) }
    }


    private fun findMethod(targetClass: String, functionName: String, argsSize: Int): Method? {
        val clazz = Class.forName(targetClass)
        return clazz.methods?.find {
            it.name == functionName
                    && it.parameterTypes.size == argsSize
        }
    }

    private fun List<Any?>.getFullArgs(composer: Composer): List<Any?> {
        return this + composer + CHANGED_FLAG + DEFAULT_FLAG
    }

    private fun Method.invokeComposable(
        composer: Composer,
        arguments: List<Any?> = emptyList()
    ): Any? {
        val newList = arguments.getFullArgs(composer)
        return invoke(null, *newList.toTypedArray())
    }

    companion object {
        private const val CHANGED_FLAG = 1
        private const val DEFAULT_FLAG = 0
        private const val LOG_TAG = "InvokeComposableService"
    }
}
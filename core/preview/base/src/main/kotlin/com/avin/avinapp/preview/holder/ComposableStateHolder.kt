package com.avin.avinapp.preview.holder

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.models.widget.ComposableDescriptor
import com.avin.avinapp.preview.extensions.typedDefaultValue

@Immutable
class ComposableStateHolder(
    val descriptor: ComposableDescriptor
) {
    private val parameterMap = mutableMapOf<String, Any?>()
    private val childrenMap = mutableMapOf<String, MutableList<ComposableStateHolder>>()

    val parameters: Map<String, Any?>
        get() = synchronized(parameterMap) { parameterMap.toMap() }

    val children: Map<String, List<ComposableStateHolder>>
        get() = synchronized(childrenMap) {
            childrenMap.mapValues { it.value.toList() }
        }

    init {
        initializeDefaultParameters()
    }

    private fun initializeDefaultParameters() {
        descriptor.parameters.forEach { param ->
            parameterMap[param.key] = param.typedDefaultValue
        }
    }

    fun updateParameter(key: String, value: Any?) {
        synchronized(parameterMap) {
            parameterMap[key] = value
        }
    }

    fun addChild(child: ComposableStateHolder, slot: String = PRIMARY_SLOT) {
        synchronized(childrenMap) {
            childrenMap.getOrPut(slot) { mutableListOf() }.add(child)
        }
    }

    fun getPrimaryChildren(): List<ComposableStateHolder> =
        synchronized(childrenMap) { childrenMap[PRIMARY_SLOT] ?: emptyList() }

    companion object {
        const val PRIMARY_SLOT = "content"
    }
}
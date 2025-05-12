package com.avin.avinapp.preview.holder

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.models.descriptor.composable.ComposableDescriptor
import com.avin.avinapp.preview.extensions.typedDefaultValue
import com.avin.avinapp.preview.utils.IdGenerator

@Immutable
class ComposableStateHolder(
    val descriptor: ComposableDescriptor,
) {
    val composableId = IdGenerator.nextId(descriptor.descriptorKey)

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
            parameterMap[param.parameterKey] = param.typedDefaultValue
        }
    }

    fun updateParameter(key: String, value: Any?) {
        synchronized(parameterMap) {
            parameterMap[key] = value
        }
    }

    fun addChild(child: ComposableStateHolder, slot: String = PRIMARY_SLOT) {
        require(descriptor.hasChildren) {
            "This component does not support adding children."
        }
        synchronized(childrenMap) {
            childrenMap.getOrPut(slot) { mutableListOf() }.add(child)
        }
    }

    fun getPrimaryChildren() = children[PRIMARY_SLOT] ?: emptyList()

    fun findHolderById(id: String): ComposableStateHolder? {
        if (!descriptor.hasChildren) return null

        val queue = ArrayDeque<ComposableStateHolder>()
        queue.add(this)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current.composableId == id) return current

            current.children.values.flatten().forEach { queue.add(it) }
        }

        return null
    }

    fun removeChildById(id: String): ComposableStateHolder? {
        if (!descriptor.hasChildren) return null

        val queue = ArrayDeque<ComposableStateHolder>()
        queue.add(this)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            current.children.forEach { (key, list) ->
                val index = list.indexOfFirst { it.composableId == id }
                if (index != -1) {
                    current.childrenMap[key]?.removeAt(index)?.let {
                        return it
                    }
                }
            }

            current.children.values.forEach { queue.addAll(it) }
        }

        return null
    }

    override fun equals(other: Any?): Boolean {
        return other is ComposableStateHolder && other.composableId == composableId
    }

    override fun hashCode(): Int {
        return composableId.hashCode()
    }

    companion object {
        const val PRIMARY_SLOT = "content"
    }
}

fun ComposableDescriptor.toHolder() = ComposableStateHolder(this)
package com.avin.avinapp.preview.holder

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.models.widget.ComposableDescriptor
import com.avin.avinapp.preview.extensions.typedDefaultValue
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Immutable
class ComposableStateHolder(
    val descriptor: ComposableDescriptor
) {
    private val mutex = Mutex()
    private val _parameters = mutableMapOf<String, Any?>()
    val parameters: Map<String, Any?> get() = _parameters.toMap()

    init {
        initializeParameters()
    }

    private fun initializeParameters() {
        descriptor.parameters.forEach { parameter ->
            _parameters[parameter.key] = parameter.typedDefaultValue
        }
    }

    suspend fun updateParameter(key: String, value: Any?) {
        mutex.withLock {
            _parameters[key] = value
        }
    }
}
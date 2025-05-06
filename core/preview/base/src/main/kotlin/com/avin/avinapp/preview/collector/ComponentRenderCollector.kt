package com.avin.avinapp.preview.collector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.avin.avinapp.preview.data.models.RenderedComponentInfo

@Stable
class ComponentRenderCollector {
    private val _componentsState = mutableStateOf(emptyList<RenderedComponentInfo>())

    val components: List<RenderedComponentInfo>
        get() = _componentsState.value

    fun updateComponent(info: RenderedComponentInfo) {
        _componentsState.value = _componentsState.value
            .filterNot { it.id == info.id } + info
    }

    fun clear() {
        _componentsState.value = emptyList()
    }
}

@Composable
fun rememberComponentRenderCollector(): ComponentRenderCollector {
    return remember { ComponentRenderCollector() }
}
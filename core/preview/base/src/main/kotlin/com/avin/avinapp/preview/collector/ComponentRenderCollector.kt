package com.avin.avinapp.preview.collector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.avin.avinapp.preview.data.models.RenderedComponentInfo

class ComponentRenderCollector {
    private val _components = mutableStateListOf<RenderedComponentInfo>()
    val components: List<RenderedComponentInfo> get() = _components

    fun updateComponent(info: RenderedComponentInfo) {
        _components.removeAll { it.id == info.id }
        _components.add(info)
    }

    fun clear() = _components.clear()
}


@Composable
fun rememberComponentRenderCollector(): ComponentRenderCollector {
    return remember { ComponentRenderCollector() }
}
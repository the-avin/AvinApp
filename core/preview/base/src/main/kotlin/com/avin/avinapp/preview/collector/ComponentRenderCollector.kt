package com.avin.avinapp.preview.collector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.avin.avinapp.preview.data.models.RenderedComponentInfo

class ComponentRenderCollector {
    @Volatile
    private var _components: List<RenderedComponentInfo> = emptyList()

    val components: List<RenderedComponentInfo>
        get() = _components

    fun updateComponent(info: RenderedComponentInfo) {
        synchronized(this) {
            _components = _components.filterNot { it.id == info.id } + info
        }
    }

    fun clear() {
        synchronized(this) {
            _components = emptyList()
        }
    }
}


@Composable
fun rememberComponentRenderCollector(): ComponentRenderCollector {
    return remember { ComponentRenderCollector() }
}
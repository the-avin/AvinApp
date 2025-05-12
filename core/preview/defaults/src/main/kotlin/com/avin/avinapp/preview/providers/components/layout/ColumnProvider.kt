package com.avin.avinapp.preview.providers.components.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avin.avinapp.preview.collector.trackRender
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.modifiers.provideModifiers
import com.avin.avinapp.preview.provider.ComposableProvider
import com.avin.avinapp.preview.registry.composable.LocalComposableRegistry

class ColumnProvider : ComposableProvider {
    override val descriptorKey: String
        get() = "foundation.column"

    @Composable
    override fun provideContent(holder: ComposableStateHolder) {
        val registry = LocalComposableRegistry.current
        Column(modifier = Modifier.trackRender(holder).provideModifiers(holder.modifiers)) {
            holder.getPrimaryChildren().forEach { registry.renderComposable(it) }
        }
    }
}
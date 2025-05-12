package com.avin.avinapp.preview.providers.components.button

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avin.avinapp.preview.collector.trackRender
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.provider.ComposableProvider
import com.avin.avinapp.preview.registry.composable.LocalComposableRegistry

internal class Material3ButtonProvider : ComposableProvider {
    override val descriptorKey: String
        get() = "material3.button"

    @Composable
    override fun provideContent(holder: ComposableStateHolder) {
        Button(
            onClick = {},
            modifier = Modifier.trackRender(holder.composableId, descriptor = holder.descriptor)
        ) {
            renderPrimaryContent(holder)
        }
    }

    @Composable
    private fun renderPrimaryContent(holder: ComposableStateHolder) {
        val registry = LocalComposableRegistry.current
        holder.getPrimaryChildren().forEach {
            registry.renderComposable(it)
        }
    }
}
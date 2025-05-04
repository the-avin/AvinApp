package com.avin.avinapp.preview.providers.button

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.provider.ComposableProvider
import com.avin.avinapp.preview.registry.LocalComposableRegistry

internal class Material3ButtonProvider : ComposableProvider {
    override val descriptorKey: String
        get() = "material3.button"

    @Composable
    override fun provideContent(holder: ComposableStateHolder) {
        Button(onClick = {}) {
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
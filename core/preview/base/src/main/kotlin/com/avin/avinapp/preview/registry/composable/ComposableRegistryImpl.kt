package com.avin.avinapp.preview.registry.composable

import androidx.compose.runtime.Composable
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.provider.ComposableProvider

class ComposableRegistryImpl : ComposableRegistry {
    private val _providers = mutableMapOf<String, ComposableProvider>()

    @Composable
    override fun renderComposable(holder: ComposableStateHolder) {
        require(_providers.contains(holder.descriptor.descriptorKey)) {
            "Composable provider for descriptor ${holder.descriptor.descriptorKey} is not registered."
        }
        _providers[holder.descriptor.descriptorKey]!!.provideContent(holder)
    }

    override fun registerComposableProvider(
        provider: ComposableProvider
    ) {
        require(!_providers.containsKey(provider.descriptorKey)) {
            "Composable provider for descriptor ${provider.descriptorKey} is already registered."
        }
        _providers[provider.descriptorKey] = provider
    }
}
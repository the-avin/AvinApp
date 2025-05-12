package com.avin.avinapp.preview.registry.modifier

import androidx.compose.ui.Modifier
import com.avin.avinapp.data.models.modifier.ModifierValues
import com.avin.avinapp.preview.provider.ModifierProvider

class ModifierRegistryImpl : ModifierRegistry {
    private val _providers = mutableMapOf<String, ModifierProvider>()

    override fun register(provider: ModifierProvider) {
        require(!_providers.containsKey(provider.descriptorKey)) {
            "Modifier provider for descriptor ${provider.descriptorKey} is already registered."
        }
        _providers[provider.descriptorKey] = provider
    }

    override fun create(
        descriptorKey: String,
        values: ModifierValues
    ): Modifier {
        require(_providers.contains(descriptorKey)) {
            "Modifier provider for descriptor $descriptorKey is not registered."
        }
        return _providers[descriptorKey]!!.provide(values)
    }
}
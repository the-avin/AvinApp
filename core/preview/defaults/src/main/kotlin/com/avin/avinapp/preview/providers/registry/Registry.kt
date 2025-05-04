package com.avin.avinapp.preview.providers.registry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.avin.avinapp.preview.providers.button.Material3ButtonProvider
import com.avin.avinapp.preview.providers.text.Material3TextComposableProvider
import com.avin.avinapp.preview.registry.ComposableRegistry
import com.avin.avinapp.preview.registry.ComposableRegistryImpl

object RegistryProvider {
    fun provide() = ComposableRegistryImpl().apply {
        // Text
        addTextComposableProviders()

        // Buttons
        addButtonComposableProviders()
    }

    private fun ComposableRegistry.addTextComposableProviders() {
        registerComposableProvider(Material3TextComposableProvider())
    }

    private fun ComposableRegistry.addButtonComposableProviders() {
        registerComposableProvider(Material3ButtonProvider())
    }
}

@Composable
fun rememberDefaultComposableRegistry() = remember {
    RegistryProvider.provide()
}
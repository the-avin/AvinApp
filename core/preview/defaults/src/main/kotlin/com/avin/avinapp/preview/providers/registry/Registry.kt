package com.avin.avinapp.preview.providers.registry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.avin.avinapp.preview.providers.components.button.Material3ButtonProvider
import com.avin.avinapp.preview.providers.components.layout.ColumnProvider
import com.avin.avinapp.preview.providers.components.text.Material3TextComposableProvider
import com.avin.avinapp.preview.providers.modifiers.size.FillMaxSizeModifierProvider
import com.avin.avinapp.preview.providers.modifiers.size.FillMaxWidthModifierProvider
import com.avin.avinapp.preview.providers.modifiers.size.HeightModifierProvider
import com.avin.avinapp.preview.registry.composable.ComposableRegistry
import com.avin.avinapp.preview.registry.composable.ComposableRegistryImpl
import com.avin.avinapp.preview.registry.modifier.ModifierRegistryImpl

object RegistryProvider {
    fun provideComposableRegistry() = ComposableRegistryImpl().apply {
        // Text
        addTextComposableProviders()

        // Buttons
        addButtonComposableProviders()

        // Layouts
        addLayoutComposableProviders()
    }

    fun provideModifierRegistry() = ModifierRegistryImpl().apply {
        register(FillMaxWidthModifierProvider())
        register(HeightModifierProvider())
        register(FillMaxSizeModifierProvider())
    }

    private fun ComposableRegistry.addTextComposableProviders() {
        registerComposableProvider(Material3TextComposableProvider())
    }

    private fun ComposableRegistry.addButtonComposableProviders() {
        registerComposableProvider(Material3ButtonProvider())
    }

    private fun ComposableRegistry.addLayoutComposableProviders() {
        registerComposableProvider(ColumnProvider())
    }
}

@Composable
fun rememberDefaultComposableRegistry() = remember {
    RegistryProvider.provideComposableRegistry()
}

@Composable
fun rememberDefaultModifierRegistry() = remember {
    RegistryProvider.provideModifierRegistry()
}
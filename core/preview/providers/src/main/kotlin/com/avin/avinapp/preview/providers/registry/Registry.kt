package com.avin.avinapp.preview.providers.registry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.avin.avinapp.preview.providers.text.Material3TextComposableProvider
import com.avin.avinapp.preview.registry.ComposableRegistryImpl

object RegistryProvider {
    fun provide() = ComposableRegistryImpl().apply {
        registerComposableProvider(Material3TextComposableProvider())
    }
}

@Composable
fun rememberDefaultComposableRegistry() = remember {
    RegistryProvider.provide()
}
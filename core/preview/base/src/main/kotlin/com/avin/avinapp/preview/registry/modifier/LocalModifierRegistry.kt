package com.avin.avinapp.preview.registry.modifier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalModifierRegistry = staticCompositionLocalOf<ModifierRegistry> {
    error("No ModifierRegistry was provided. Make sure to wrap your Composable with CompositionLocalProvider(LocalModifierRegistry provides registry).")
}


@Composable
fun ProvideModifierRegistry(registry: ModifierRegistry, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalModifierRegistry provides registry, content = content)
}
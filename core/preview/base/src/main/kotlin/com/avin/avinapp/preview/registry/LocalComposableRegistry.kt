package com.avin.avinapp.preview.registry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalComposableRegistry = staticCompositionLocalOf<ComposableRegistry> {
    error("No ComposableRegistry was provided. Make sure to wrap your Composable with CompositionLocalProvider(LocalComposableRegistry provides registry).")
}


@Composable
fun ProvideComposableRegistry(registry: ComposableRegistry, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalComposableRegistry provides registry, content = content)
}
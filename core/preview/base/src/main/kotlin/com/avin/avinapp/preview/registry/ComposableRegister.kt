package com.avin.avinapp.preview.registry

import androidx.compose.runtime.Composable
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.provider.ComposableProvider

interface ComposableRegistry {
    @Composable
    fun renderComposable(holder: ComposableStateHolder)

    fun registerComposableProvider(provider: ComposableProvider)
}
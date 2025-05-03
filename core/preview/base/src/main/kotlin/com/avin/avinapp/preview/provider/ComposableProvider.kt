package com.avin.avinapp.preview.provider

import androidx.compose.runtime.Composable
import com.avin.avinapp.preview.holder.ComposableStateHolder

interface ComposableProvider {
    val descriptorKey: String

    @Composable
    fun provideContent(holder: ComposableStateHolder)
}
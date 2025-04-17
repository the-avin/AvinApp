package com.avin.avinapp.collector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

val LocalComponentRenderCollector =
    compositionLocalOf<ComponentRenderCollector> { error("CompositionLocalCollector not provided") }


@Composable
internal fun NewLocalComponentRenderCollector(
    collector: ComponentRenderCollector,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalComponentRenderCollector provides collector,
        content = content
    )
}
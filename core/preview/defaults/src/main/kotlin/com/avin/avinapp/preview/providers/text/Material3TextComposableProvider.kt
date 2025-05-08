package com.avin.avinapp.preview.providers.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avin.avinapp.preview.collector.trackRender
import com.avin.avinapp.preview.holder.ComposableStateHolder
import com.avin.avinapp.preview.provider.ComposableProvider

internal class Material3TextComposableProvider : ComposableProvider {
    override val descriptorKey: String
        get() = "material3.text"

    @Composable
    override fun provideContent(holder: ComposableStateHolder) {
        Text(
            text = holder.parameters["label"]?.toString().orEmpty(),
            modifier = Modifier.trackRender(id = holder.composableId, holder.descriptor),
            maxLines = (holder.parameters["maxLines"] as? Int) ?: Int.MAX_VALUE
        )
    }
}
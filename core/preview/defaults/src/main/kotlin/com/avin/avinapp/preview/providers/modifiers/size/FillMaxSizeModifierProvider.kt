package com.avin.avinapp.preview.providers.modifiers.size

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.avin.avinapp.data.models.modifier.ModifierValues
import com.avin.avinapp.preview.provider.ModifierProvider
import com.avin.avinapp.preview.providers.extensions.asFloat

class FillMaxSizeModifierProvider : ModifierProvider {
    override val descriptorKey: String
        get() = "fillMaxSize"

    override fun provide(values: ModifierValues): Modifier {
        return Modifier.fillMaxSize(
            fraction = values["fraction"]?.asFloat() ?: 1f
        )
    }
}
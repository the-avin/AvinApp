package com.avin.avinapp.preview.providers.modifiers.size

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.avin.avinapp.data.models.modifier.ModifierValues
import com.avin.avinapp.preview.provider.ModifierProvider

class FillMaxWidthModifierProvider : ModifierProvider {
    override val descriptorKey: String
        get() = "fillMaxWidth"

    override fun provide(values: ModifierValues): Modifier {
        return Modifier.fillMaxWidth()
    }
}
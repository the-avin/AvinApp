package com.avin.avinapp.preview.providers.modifiers.size

import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.avin.avinapp.data.models.modifier.ModifierValues
import com.avin.avinapp.preview.provider.ModifierProvider

class HeightModifierProvider : ModifierProvider {
    override val descriptorKey: String
        get() = "height"

    override fun provide(values: ModifierValues): Modifier {
        val height = values["height"] as? Dp
        return height?.let { Modifier.height(it) } ?: Modifier
    }
}
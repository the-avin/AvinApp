package com.avin.avinapp.preview.registry.modifier

import androidx.compose.ui.Modifier
import com.avin.avinapp.data.models.modifier.ModifierValues
import com.avin.avinapp.preview.provider.ModifierProvider

interface ModifierRegistry {
    fun register(provider: ModifierProvider)

    fun create(descriptorKey: String, values: ModifierValues): Modifier
}
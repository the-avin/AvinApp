package com.avin.avinapp.preview.provider

import androidx.compose.ui.Modifier
import com.avin.avinapp.data.models.modifier.ModifierValues

interface ModifierProvider {
    val descriptorKey: String

    fun provide(values: ModifierValues): Modifier
}
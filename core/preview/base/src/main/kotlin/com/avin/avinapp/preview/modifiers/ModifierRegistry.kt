package com.avin.avinapp.preview.modifiers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.avin.avinapp.data.models.modifier.ModifierValues
import com.avin.avinapp.preview.registry.modifier.LocalModifierRegistry
import com.avin.avinapp.preview.registry.modifier.ModifierRegistry

@Composable
fun Modifier.provideModifiers(
    parameters: Map<String, ModifierValues>,
    registry: ModifierRegistry = LocalModifierRegistry.current
): Modifier {
    return parameters.entries.fold(this) { acc, (key, values) ->
        acc.then(registry.create(key, values))
    }
}
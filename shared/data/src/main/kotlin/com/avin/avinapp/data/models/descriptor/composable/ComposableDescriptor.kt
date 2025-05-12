package com.avin.avinapp.data.models.descriptor.composable

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.models.descriptor.default_modifier.DefaultModifierDescriptor
import com.avin.avinapp.data.models.descriptor.parameter.ParameterDescriptor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ComposableDescriptor(
    val name: String,
    @SerialName("descriptor_key")
    val descriptorKey: String,
    val group: String? = null,
    val parameters: List<ParameterDescriptor> = emptyList(),
    @SerialName("default_modifiers")
    val defaultModifiers: List<DefaultModifierDescriptor> = emptyList(),
    val hasChildren: Boolean
)

fun List<ComposableDescriptor>.findByDescriptorKey(key: String) = find { it.descriptorKey == key }
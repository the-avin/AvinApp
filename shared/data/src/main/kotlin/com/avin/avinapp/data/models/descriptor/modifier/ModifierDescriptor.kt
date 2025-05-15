package com.avin.avinapp.data.models.descriptor.modifier

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.models.descriptor.parameter.ParameterDescriptor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ModifierDescriptor(
    @SerialName("descriptor_key")
    val descriptorKey: String,
    val name: String = descriptorKey.replaceFirstChar { it.uppercase() },
    val parameters: List<ParameterDescriptor> = emptyList()
)
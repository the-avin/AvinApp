package com.avin.avinapp.data.models.descriptor.default_modifier

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.domain.parameter.type.ParameterType
import com.avin.avinapp.data.domain.parameter.value.ParameterValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class DefaultModifierDescriptor(
    @SerialName("descriptor_key")
    val descriptorKey: String,
    val parameters: List<Parameter> = emptyList()
) {
    @Serializable
    @Immutable
    data class Parameter(
        val key: String,
        val type: ParameterType,
        val value: ParameterValue? = null,
    )
}
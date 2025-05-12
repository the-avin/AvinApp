package com.avin.avinapp.data.models.descriptor.default_modifier

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.domain.parameter.ParameterType
import com.avin.avinapp.data.domain.parameter.ParameterValueSource
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
        val source: ParameterValueSource = ParameterValueSource.STATIC,
        val value: String? = null,
    )
}
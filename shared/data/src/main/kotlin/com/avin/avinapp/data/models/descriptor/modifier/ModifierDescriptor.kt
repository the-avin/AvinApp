package com.avin.avinapp.data.models.descriptor.modifier

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.domain.parameter.ParameterValueSource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ModifierDescriptor(
    @SerialName("modifier_key")
    val modifierKey: String,
    val parameters: List<Parameter>
) {
    @Serializable
    @Immutable
    data class Parameter(
        val parameterKey: String,
        val source: ParameterValueSource,
        val value: String
    )
}
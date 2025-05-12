package com.avin.avinapp.data.models.widget

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.domain.parameter.ParameterType
import com.avin.avinapp.data.domain.parameter.ParameterValueSource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ComposableDescriptor(
    val name: String,
    @SerialName("descriptor_key")
    val descriptorKey: String,
    val group: String? = null,
    val parameters: List<Parameter> = emptyList(),
    val defaultModifiers: List<Modifier> = emptyList(),
    val hasChildren: Boolean
) {
    @Serializable
    @Immutable
    data class Parameter(
        val name: String,
        @SerialName("parameter_key")
        val parameterKey: String,
        val type: ParameterType,
        @SerialName("default_value")
        val defaultValue: ParameterValue? = null
    ) {
        @Serializable
        @Immutable
        data class ParameterValue(
            val source: ParameterValueSource,
            val value: String
        )
    }

    @Serializable
    @Immutable
    data class Modifier(
        @SerialName("modifier_key")
        val modifierKey: String,
    )
}

fun List<ComposableDescriptor>.findByDescriptorKey(key: String) = find { it.descriptorKey == key }
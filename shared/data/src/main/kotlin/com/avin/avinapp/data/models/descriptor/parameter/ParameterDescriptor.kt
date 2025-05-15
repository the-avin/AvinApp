package com.avin.avinapp.data.models.descriptor.parameter

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.domain.parameter.type.ParameterType
import com.avin.avinapp.data.domain.parameter.value.ParameterValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ParameterDescriptor(
    @SerialName("descriptor_key")
    val descriptorKey: String,
    val name: String = descriptorKey.replaceFirstChar { it.uppercase() },
    val type: ParameterType,
    @SerialName("default_value")
    val defaultValue: ParameterValue? = null
)
package com.avin.avinapp.data.models.descriptor.parameter

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.domain.parameter.ParameterType
import com.avin.avinapp.data.domain.parameter.ParameterValueSource
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ParameterDescriptor(
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
package com.avin.avinapp.data.models.widget

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.domain.parameter.ParameterType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ComposableDescriptor(
    @SerialName("descriptor_key")
    val descriptorKey: String,
    val parameters: List<Parameter>,
    val hasChildren: Boolean
) {
    @Serializable
    @Immutable
    data class Parameter(
        val name: String,
        val key: String,
        val type: ParameterType,
        val defaultValue: String? = null
    )
}
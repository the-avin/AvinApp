package com.avin.avinapp.data.domain.parameter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface ParameterType {
    @Serializable
    @SerialName("StringType")
    data object StringType : ParameterType

    @Serializable
    @SerialName("IntType")
    data object IntType : ParameterType

    @Serializable
    @SerialName("FloatType")
    data object FloatType : ParameterType

    @Serializable
    @SerialName("DpType")
    data object DpType : ParameterType

    @Serializable
    @SerialName("SpType")
    data object SpType : ParameterType

    @Serializable
    @SerialName("ColorType")
    data object ColorType : ParameterType

    @Serializable
    @SerialName("BooleanType")
    data object BooleanType : ParameterType
}
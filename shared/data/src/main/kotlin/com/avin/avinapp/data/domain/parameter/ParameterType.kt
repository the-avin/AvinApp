package com.avin.avinapp.data.domain.parameter

import kotlinx.serialization.Serializable

@Serializable
sealed interface ParameterType {
    @Serializable
    data object StringType : ParameterType

    @Serializable
    data object IntType : ParameterType

    @Serializable
    data object FloatType : ParameterType

    @Serializable
    data object DpType : ParameterType

    @Serializable
    data object SpType : ParameterType

    @Serializable
    data object ColorType : ParameterType

    @Serializable
    data object BooleanType : ParameterType
}
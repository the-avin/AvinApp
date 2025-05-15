package com.avin.avinapp.data.domain.parameter

import com.avin.avinapp.data.domain.parameter.limits.NumberLimitable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class FieldType {
    INPUT,
    SLIDER
}

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
    data class FloatType(
        @SerialName("field_type")
        val fieldType: FieldType = FieldType.INPUT,
        @SerialName("min_value") override val minValue: Float? = null,
        @SerialName("max_value") override val maxValue: Float? = null,
    ) : ParameterType, NumberLimitable<Float>()

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
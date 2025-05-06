package com.avin.avinapp.preview.utils

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avin.avinapp.data.domain.parameter.ParameterType
import com.avin.avinapp.data.domain.parameter.ParameterValueSource
import com.avin.avinapp.preview.extensions.toComposeColor

const val MAX_VALUE = "MAX"
const val MIN_VALUE = "MIN"

fun convertParameterToType(
    value: String?,
    type: ParameterType,
    source: ParameterValueSource
): Any? {
    if (value == null) return null

    fun parseNumericOrLimit(
        value: String,
        parse: () -> Number,
        maxValue: Number,
        minValue: Number
    ): Number {
        return when (value) {
            MAX_VALUE -> maxValue
            MIN_VALUE -> minValue
            else -> parse()
        }
    }

    return when (source) {
        ParameterValueSource.STATIC -> {
            when (type) {
                is ParameterType.BooleanType -> value.toBoolean()
                is ParameterType.FloatType -> parseNumericOrLimit(
                    value,
                    { value.toFloat() },
                    Float.MAX_VALUE,
                    Float.MIN_VALUE
                )

                is ParameterType.IntType -> parseNumericOrLimit(
                    value,
                    { value.toInt() },
                    Int.MAX_VALUE,
                    Int.MIN_VALUE
                )

                is ParameterType.DpType -> {
                    val num = parseNumericOrLimit(
                        value,
                        { value.toFloat() },
                        Float.MAX_VALUE,
                        Float.MIN_VALUE
                    )
                    (num.toFloat()).dp
                }

                is ParameterType.SpType -> {
                    val num = parseNumericOrLimit(
                        value,
                        { value.toFloat() },
                        Float.MAX_VALUE,
                        Float.MIN_VALUE
                    )
                    (num.toFloat()).sp
                }

                is ParameterType.ColorType -> value.toComposeColor()
                else -> value
            }
        }

        ParameterValueSource.CONTEXTUAL -> null
    }
}
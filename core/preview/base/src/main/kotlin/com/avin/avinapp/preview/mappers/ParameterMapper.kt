package com.avin.avinapp.preview.mappers

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avin.avinapp.data.domain.parameter.ParameterType
import com.avin.avinapp.data.domain.parameter.ParameterValueSource
import com.avin.avinapp.preview.extensions.toComposeColor

const val MAX_VALUE = "MAX"
const val MIN_VALUE = "MIN"

private fun parseBoolean(value: String): Boolean {
    return value.toBoolean()
}

private fun parseFloat(value: String): Float? {
    return parseNumericOrLimit(
        value,
        { value.toFloatOrNull() },
        Float.MAX_VALUE,
        Float.MIN_VALUE
    )
}

private fun parseInt(value: String): Int? {
    return parseNumericOrLimit(
        value,
        { value.toIntOrNull() },
        Int.MAX_VALUE,
        Int.MIN_VALUE
    )
}

private fun parseColor(value: String): Color {
    return value.toComposeColor()
}

private fun <T : Number> parseNumericOrLimit(
    value: String,
    parse: () -> T?,
    maxValue: T,
    minValue: T
): T? {
    return when (value) {
        MAX_VALUE -> maxValue
        MIN_VALUE -> minValue
        else -> parse()
    }
}

fun convertParameterToType(
    value: String?,
    type: ParameterType,
    source: ParameterValueSource
): Any? {
    if (value == null || source == ParameterValueSource.CONTEXTUAL) return null

    return when (type) {
        is ParameterType.BooleanType -> parseBoolean(value)
        is ParameterType.FloatType -> parseFloat(value)
        is ParameterType.IntType -> parseInt(value)
        is ParameterType.DpType -> parseFloat(value)?.dp
        is ParameterType.SpType -> parseFloat(value)?.sp
        is ParameterType.ColorType -> parseColor(value)
        is ParameterType.StringType -> value
    }
}
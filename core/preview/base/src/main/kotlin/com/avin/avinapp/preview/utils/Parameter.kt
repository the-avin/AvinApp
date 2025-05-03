package com.avin.avinapp.preview.utils

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avin.avinapp.data.domain.parameter.ParameterType
import com.avin.avinapp.preview.extensions.toComposeColor

fun convertParameterToType(
    value: String?,
    type: ParameterType
): Any? {
    if (value == null) return null

    return when (type) {
        is ParameterType.BooleanType -> value.toBoolean()
        is ParameterType.FloatType -> value.toFloat()
        is ParameterType.IntType -> value.toInt()
        is ParameterType.ColorType -> value.toComposeColor()
        is ParameterType.DpType -> value.toFloat().dp
        is ParameterType.SpType -> value.toFloat().sp
        else -> value
    }
}
package com.avin.avinapp.data.extensions.parameter

import com.avin.avinapp.data.domain.parameter.type.ParameterType

val ParameterType.FloatType.valueRange: ClosedFloatingPointRange<Float>?
    get() = if (hasRange()) minValue!!..maxValue!! else null
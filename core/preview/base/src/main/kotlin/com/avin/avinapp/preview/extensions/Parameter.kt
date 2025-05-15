package com.avin.avinapp.preview.extensions

import com.avin.avinapp.data.models.descriptor.parameter.ParameterDescriptor
import com.avin.avinapp.preview.mappers.convertParameterToType

val ParameterDescriptor.typedDefaultValue: Any?
    get() {
        return convertParameterToType(
            value = defaultValue?.value, type = type, source = defaultValue?.source ?: return null
        )
    }
package com.avin.avinapp.preview.extensions

import com.avin.avinapp.data.models.descriptor.parameter.ParameterDescriptor
import com.avin.avinapp.preview.mappers.convertParameterToType

val ParameterDescriptor.typedDefaultValue: Any?
    get() {
        return defaultValue?.let {
            convertParameterToType(
                valueObject = it, type = type
            )
        }
    }
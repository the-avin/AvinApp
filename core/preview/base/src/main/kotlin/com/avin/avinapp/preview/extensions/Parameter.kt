package com.avin.avinapp.preview.extensions

import com.avin.avinapp.data.models.widget.ComposableDescriptor
import com.avin.avinapp.preview.utils.convertParameterToType

val ComposableDescriptor.Parameter.typedDefaultValue: Any?
    get() {
        return convertParameterToType(
            value = defaultValue?.value, type = type, source = defaultValue?.source ?: return null
        )
    }
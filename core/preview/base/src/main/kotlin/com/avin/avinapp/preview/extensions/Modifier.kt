package com.avin.avinapp.preview.extensions

import com.avin.avinapp.data.models.descriptor.default_modifier.DefaultModifierDescriptor
import com.avin.avinapp.data.models.modifier.ModifierValues
import com.avin.avinapp.preview.mappers.convertParameterToType

val DefaultModifierDescriptor.defaultValues: ModifierValues
    get() = parameters.associate {
        it.key to convertParameterToType(it.value, it.type, it.source)
    }.toMutableMap()
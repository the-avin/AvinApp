package com.avin.avinapp.data.serialization.parameter

import com.avin.avinapp.data.domain.parameter.ParameterType
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val parameterTypeSerializersModule: SerializersModule
    get() = SerializersModule {
        polymorphic(ParameterType::class) {
            subclass(ParameterType.StringType::class, ParameterType.StringType.serializer())
            subclass(ParameterType.IntType::class, ParameterType.IntType.serializer())
            subclass(ParameterType.FloatType::class, ParameterType.FloatType.serializer())
            subclass(ParameterType.DpType::class, ParameterType.DpType.serializer())
            subclass(ParameterType.SpType::class, ParameterType.SpType.serializer())
            subclass(ParameterType.ColorType::class, ParameterType.ColorType.serializer())
            subclass(ParameterType.BooleanType::class, ParameterType.BooleanType.serializer())
        }
    }
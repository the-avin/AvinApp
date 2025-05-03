package com.avin.avinapp.data.serialization

import com.avin.avinapp.data.serialization.parameter.parameterTypeSerializersModule
import kotlinx.serialization.json.Json

object JsonConfig {
    const val PARAMETER_TYPE_DISCRIMINATOR = "type"

    val typeJson: Json
        get() = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            encodeDefaults = true
            classDiscriminator = "type"
            serializersModule = parameterTypeSerializersModule
        }
}
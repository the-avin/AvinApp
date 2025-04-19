package com.avin.avinapp.data.serializers.widget

import com.avin.avinapp.data.models.widget.ComposableDescriptor
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object ArgumentValueSerializer :
    JsonContentPolymorphicSerializer<ComposableDescriptor.Argument.ArgumentValue>(
        ComposableDescriptor.Argument.ArgumentValue::class
    ) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ComposableDescriptor.Argument.ArgumentValue> {
        return when (val type = element.jsonObject["type"]?.jsonPrimitive?.content) {
            "primitive" -> ComposableDescriptor.Argument.ArgumentValue.Primitive.serializer()
            "nested" -> ComposableDescriptor.Argument.ArgumentValue.Nested.serializer()
            "composable" -> ComposableDescriptor.Argument.ArgumentValue.Composable.serializer()
            "list" -> ComposableDescriptor.Argument.ArgumentValue.ListOf.serializer()
            "modifier" -> ComposableDescriptor.Argument.ArgumentValue.Modifier.serializer()
            "lambda" -> ComposableDescriptor.Argument.ArgumentValue.Lambda.serializer()
            else -> error("Unknown Argument.Value type: $type")
        }
    }
}
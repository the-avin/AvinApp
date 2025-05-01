package com.avin.avinapp.data.serializers.widget

import com.avin.avinapp.data.models.widget.ComposableDescriptorReflection
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object ArgumentValueSerializer :
    JsonContentPolymorphicSerializer<ComposableDescriptorReflection.Argument.ArgumentValue>(
        ComposableDescriptorReflection.Argument.ArgumentValue::class
    ) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<ComposableDescriptorReflection.Argument.ArgumentValue> {
        return when (val type = element.jsonObject["type"]?.jsonPrimitive?.content) {
            "primitive" -> ComposableDescriptorReflection.Argument.ArgumentValue.Primitive.serializer()
            "nested" -> ComposableDescriptorReflection.Argument.ArgumentValue.Nested.serializer()
            "composable" -> ComposableDescriptorReflection.Argument.ArgumentValue.Composable.serializer()
            "list" -> ComposableDescriptorReflection.Argument.ArgumentValue.ListOf.serializer()
            "modifier" -> ComposableDescriptorReflection.Argument.ArgumentValue.Modifier.serializer()
            "lambda" -> ComposableDescriptorReflection.Argument.ArgumentValue.Lambda.serializer()
            else -> error("Unknown Argument.Value type: $type")
        }
    }
}
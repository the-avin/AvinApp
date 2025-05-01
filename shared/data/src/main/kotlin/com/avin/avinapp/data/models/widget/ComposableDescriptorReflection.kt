package com.avin.avinapp.data.models.widget

import androidx.compose.runtime.Immutable
import com.avin.avinapp.data.serializers.widget.ArgumentValueSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a composable function call with reflection-based metadata.
 *
 * @param targetClass The fully qualified name of the Kotlin class containing the composable function.
 * @param functionName The name of the composable function.
 * @param arguments List of arguments to pass to the function, including nested or dynamic values.
 * @param supportsChildren Whether this composable supports nested children.
 */
@Immutable
@Serializable
@Deprecated(
    message = "ComposableDescriptorReflection is deprecated. Use ComposableDescriptor instead for better type safety, performance.",
    replaceWith = ReplaceWith("ComposableDescriptor")
)
data class ComposableDescriptorReflection(
    val targetClass: String,
    val functionName: String,
    val arguments: List<Argument>,
    val supportsChildren: Boolean = false,
) {
    @Immutable
    @Serializable
    data class Argument(
        val name: String,
        val targetClass: String? = null,
        val className: String? = null,
        val type: ArgumentType = ArgumentType.OTHER,
        @SerialName("is_editable")
        val isEditable: Boolean = true,
        val nullable: Boolean = false,
        @Serializable(with = ArgumentValueSerializer::class)
        val arguments: ArgumentValue? = null,
        @SerialName("default_value")
        @Serializable(with = ArgumentValueSerializer::class)
        val defaultValue: ArgumentValue? = null
    ) {
        @Immutable
        @Serializable
        sealed interface ArgumentValue {
            @Serializable
            @JvmInline
            value class Primitive(val raw: String? = null) : ArgumentValue

            @Serializable
            @JvmInline
            value class Lambda(val givenArguments: List<Argument> = emptyList()) : ArgumentValue

            @Serializable
            data class Nested(val argument: Argument? = null) : ArgumentValue

            @Serializable
            data object Modifier : ArgumentValue

            @Serializable
            data class Composable(
                val descriptor: ComposableDescriptorReflection? = null,
                val argumentSize: Int
            ) : ArgumentValue

            @Serializable
            data class Variable(val targetClass: String, val variableName: String) : ArgumentValue

            @Serializable
            data class ListOf(val items: List<ArgumentValue> = emptyList()) : ArgumentValue
        }
    }
}

fun ComposableDescriptorReflection.Argument.ArgumentValue.asPrimitive() =
    this as ComposableDescriptorReflection.Argument.ArgumentValue.Primitive

val buttonDescriptor = ComposableDescriptorReflection(
    targetClass = "androidx.compose.material3.ButtonKt",
    functionName = "Button",
    supportsChildren = true,
    arguments = listOf(
        ComposableDescriptorReflection.Argument(
            name = "onClick",
            type = ArgumentType.OTHER,
            isEditable = true,
            nullable = false,
            arguments = ComposableDescriptorReflection.Argument.ArgumentValue.Lambda()
        ),
        ComposableDescriptorReflection.Argument(
            name = "modifier",
            type = ArgumentType.OTHER,
            isEditable = true,
            nullable = false,
            arguments = ComposableDescriptorReflection.Argument.ArgumentValue.Modifier,
            defaultValue = ComposableDescriptorReflection.Argument.ArgumentValue.Modifier
        ),
        ComposableDescriptorReflection.Argument(
            name = "enabled",
            type = ArgumentType.BOOLEAN,
            isEditable = true,
            nullable = false,
            defaultValue = ComposableDescriptorReflection.Argument.ArgumentValue.Primitive("true")
        ),
        ComposableDescriptorReflection.Argument(
            name = "shape",
            type = ArgumentType.OTHER,
            className = "androidx.compose.foundation.shape.Shape",
            isEditable = true,
            nullable = false,
            defaultValue = ComposableDescriptorReflection.Argument.ArgumentValue.Variable(
                targetClass = "androidx.compose.material3.ButtonDefaults",
                variableName = "shape",
            )
        ),
        ComposableDescriptorReflection.Argument(
            name = "colors",
            type = ArgumentType.OTHER,
            className = "androidx.compose.material3.ButtonColors",
            isEditable = true,
            nullable = false,
            defaultValue = ComposableDescriptorReflection.Argument.ArgumentValue.Composable(
                descriptor = ComposableDescriptorReflection(
                    targetClass = "androidx.compose.material3.ButtonDefaults",
                    functionName = "buttonColors",
                    arguments = emptyList(),
                ),
                argumentSize = 0
            )
        ),
        ComposableDescriptorReflection.Argument(
            name = "elevation",
            type = ArgumentType.OTHER,
            className = "androidx.compose.material3.ButtonElevation",
            isEditable = true,
            nullable = true,
            defaultValue = ComposableDescriptorReflection.Argument.ArgumentValue.Composable(
                descriptor = ComposableDescriptorReflection(
                    targetClass = "androidx.compose.material3.ButtonDefaults",
                    functionName = "buttonElevation",
                    arguments = emptyList(),
                ),
                argumentSize = 3
            )
        ),
        ComposableDescriptorReflection.Argument(
            name = "border",
            type = ArgumentType.OTHER,
            className = "androidx.compose.foundation.BorderStroke",
            isEditable = true,
            nullable = true,
            defaultValue = ComposableDescriptorReflection.Argument.ArgumentValue.Primitive(null)
        ),
        ComposableDescriptorReflection.Argument(
            name = "contentPadding",
            type = ArgumentType.OTHER,
            className = "androidx.compose.foundation.layout.PaddingValues",
            isEditable = true,
            nullable = false,
            defaultValue = ComposableDescriptorReflection.Argument.ArgumentValue.Variable(
                targetClass = "androidx.compose.material3.ButtonDefaults",
                variableName = "ContentPadding",
            )
        ),
        ComposableDescriptorReflection.Argument(
            name = "interactionSource",
            type = ArgumentType.OTHER,
            className = "androidx.compose.foundation.interaction.MutableInteractionSource",
            isEditable = true,
            nullable = true,
            defaultValue = ComposableDescriptorReflection.Argument.ArgumentValue.Primitive(null)
        ),
        ComposableDescriptorReflection.Argument(
            name = "content",
            type = ArgumentType.OTHER,
            isEditable = true,
            nullable = false,
            arguments = ComposableDescriptorReflection.Argument.ArgumentValue.Composable(
                argumentSize = 0
            )
        )
    )
)
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
data class ComposableDescriptor(
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
                val descriptor: ComposableDescriptor? = null,
                val argumentSize: Int
            ) : ArgumentValue

            @Serializable
            data class Variable(val targetClass: String, val variableName: String) : ArgumentValue

            @Serializable
            data class ListOf(val items: List<ArgumentValue> = emptyList()) : ArgumentValue
        }
    }
}

fun ComposableDescriptor.Argument.ArgumentValue.asPrimitive() =
    this as ComposableDescriptor.Argument.ArgumentValue.Primitive

val buttonDescriptor = ComposableDescriptor(
    targetClass = "androidx.compose.material3.ButtonKt",
    functionName = "Button",
    supportsChildren = true,
    arguments = listOf(
        ComposableDescriptor.Argument(
            name = "onClick",
            type = ArgumentType.OTHER,
            isEditable = true,
            nullable = false,
            arguments = ComposableDescriptor.Argument.ArgumentValue.Lambda()
        ),
        ComposableDescriptor.Argument(
            name = "modifier",
            type = ArgumentType.OTHER,
            isEditable = true,
            nullable = false,
            arguments = ComposableDescriptor.Argument.ArgumentValue.Modifier,
            defaultValue = ComposableDescriptor.Argument.ArgumentValue.Modifier
        ),
        ComposableDescriptor.Argument(
            name = "enabled",
            type = ArgumentType.BOOLEAN,
            isEditable = true,
            nullable = false,
            defaultValue = ComposableDescriptor.Argument.ArgumentValue.Primitive("true")
        ),
        ComposableDescriptor.Argument(
            name = "shape",
            type = ArgumentType.OTHER,
            className = "androidx.compose.foundation.shape.Shape",
            isEditable = true,
            nullable = false,
            defaultValue = ComposableDescriptor.Argument.ArgumentValue.Variable(
                targetClass = "androidx.compose.material3.ButtonDefaults",
                variableName = "shape",
            )
        ),
        ComposableDescriptor.Argument(
            name = "colors",
            type = ArgumentType.OTHER,
            className = "androidx.compose.material3.ButtonColors",
            isEditable = true,
            nullable = false,
            defaultValue = ComposableDescriptor.Argument.ArgumentValue.Composable(
                descriptor = ComposableDescriptor(
                    targetClass = "androidx.compose.material3.ButtonDefaults",
                    functionName = "buttonColors",
                    arguments = emptyList(),
                ),
                argumentSize = 0
            )
        ),
        ComposableDescriptor.Argument(
            name = "elevation",
            type = ArgumentType.OTHER,
            className = "androidx.compose.material3.ButtonElevation",
            isEditable = true,
            nullable = true,
            defaultValue = ComposableDescriptor.Argument.ArgumentValue.Composable(
                descriptor = ComposableDescriptor(
                    targetClass = "androidx.compose.material3.ButtonDefaults",
                    functionName = "buttonElevation",
                    arguments = emptyList(),
                ),
                argumentSize = 3
            )
        ),
        ComposableDescriptor.Argument(
            name = "border",
            type = ArgumentType.OTHER,
            className = "androidx.compose.foundation.BorderStroke",
            isEditable = true,
            nullable = true,
            defaultValue = ComposableDescriptor.Argument.ArgumentValue.Primitive(null)
        ),
        ComposableDescriptor.Argument(
            name = "contentPadding",
            type = ArgumentType.OTHER,
            className = "androidx.compose.foundation.layout.PaddingValues",
            isEditable = true,
            nullable = false,
            defaultValue = ComposableDescriptor.Argument.ArgumentValue.Variable(
                targetClass = "androidx.compose.material3.ButtonDefaults",
                variableName = "ContentPadding",
            )
        ),
        ComposableDescriptor.Argument(
            name = "interactionSource",
            type = ArgumentType.OTHER,
            className = "androidx.compose.foundation.interaction.MutableInteractionSource",
            isEditable = true,
            nullable = true,
            defaultValue = ComposableDescriptor.Argument.ArgumentValue.Primitive(null)
        ),
        ComposableDescriptor.Argument(
            name = "content",
            type = ArgumentType.OTHER,
            isEditable = true,
            nullable = false,
            arguments = ComposableDescriptor.Argument.ArgumentValue.Composable(
                argumentSize = 0
            )
        )
    )
)
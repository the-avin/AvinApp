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
        val type: ArgumentType,
        @SerialName("is_editable")
        val isEditable: Boolean,
        val nullable: Boolean,
        @SerialName("value")
        @Serializable(with = ArgumentValueSerializer::class)
        val arguments: ArgumentValue? = null,
        val defaultValue : ArgumentValue? = null
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
            data class Composable(val descriptor: ComposableDescriptor? = null) : ArgumentValue

            @Serializable
            data class ListOf(val items: List<ArgumentValue> = emptyList()) : ArgumentValue
        }
    }
}

val buttonDescriptor = ComposableDescriptor(
    targetClass = "androidx.compose.material3.ButtonKt",
    functionName = "Button",
    arguments = listOf()
)
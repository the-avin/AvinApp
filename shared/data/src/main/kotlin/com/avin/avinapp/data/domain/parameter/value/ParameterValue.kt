package com.avin.avinapp.data.domain.parameter.value

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Defines the type of a value that is resolved dynamically from the current theme or environment context.
 *
 * This is used to distinguish between different kinds of contextual values,
 * such as colors, typography styles, etc.
 */
enum class ContextualSource {
    COLOR,
    TYPOGRAPHY
}

@Serializable
sealed interface ParameterValue {

    /**
     * A fixed, literal value explicitly defined in the source code.
     *
     * Examples: a hardcoded string like "Hello", a number like 42, or a hexadecimal color like "#FF0000".
     * This value remains the same regardless of theme, environment, or runtime conditions.
     */
    @Serializable
    @SerialName("static")
    data class Static(val value: String? = null) : ParameterValue

    /**
     * A value that is derived from or resolved based on the current context.
     *
     * Examples: a color from MaterialTheme (e.g., `colorScheme.primary`),
     * a text style from the current typography, or any runtime-resolved resource.
     */
    @Serializable
    @SerialName("contextual")
    data class Contextual(
        val source: ContextualSource,
        val path: String
    ) : ParameterValue
}
package com.avin.avinapp.data.domain.parameter

/**
 * Defines the source of a parameter's value.
 *
 * This is used to distinguish between values that are hardcoded versus those that
 * are resolved dynamically based on the current context (such as theme or environment).
 */
enum class ParameterValueSource {

    /**
     * A constant or literal value that is hardcoded and does not depend on any external context.
     *
     * Examples: a fixed string like "Hello", a number like 42, or a specific color code.
     */
    CONSTANT,

    /**
     * A value that is derived from or resolved based on the current context.
     *
     * Examples: a color from MaterialTheme (e.g., `colorScheme.primary`),
     * a text style from the current typography, or any runtime-resolved resource.
     */
    CONTEXTUAL
}
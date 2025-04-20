package com.avin.avinapp.theme.data

enum class Theme {
    DARK, LIGHT, SYSTEM;

    companion object {
        fun fromString(theme: String?): Theme {
            return theme?.let { requiredTheme ->
                entries.firstOrNull {
                    it.name.equals(
                        requiredTheme,
                        ignoreCase = true
                    )
                }
            } ?: SYSTEM
        }
    }

    override fun toString(): String = name.lowercase()
}
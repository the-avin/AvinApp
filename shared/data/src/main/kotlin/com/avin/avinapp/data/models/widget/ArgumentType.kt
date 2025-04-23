package com.avin.avinapp.data.models.widget

enum class ArgumentType {
    STRING,
    INT,
    DP,
    SP,
    COLOR,
    BOOLEAN,
    OTHER
}

val ArgumentType.isPrimitive: Boolean
    get() = this != ArgumentType.OTHER
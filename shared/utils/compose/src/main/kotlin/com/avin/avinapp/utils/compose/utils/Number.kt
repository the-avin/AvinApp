package com.avin.avinapp.utils.compose.utils

fun Float.formatSmart(): String =
    if (this % 1f == 0f) this.toInt().toString() else String.format("%.1f", this)

fun Double.formatSmart(): String =
    if (this % 1f == 0.0) this.toInt().toString() else String.format("%.1f", this)
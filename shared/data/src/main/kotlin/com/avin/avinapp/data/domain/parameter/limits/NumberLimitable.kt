package com.avin.avinapp.data.domain.parameter.limits

abstract class NumberLimitable<T : Number> {
    abstract val minValue: T?
    abstract val maxValue: T?

    fun hasRange() = minValue != null && maxValue != null
}
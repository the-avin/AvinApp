package com.avin.avinapp.preview.utils

object IdGenerator {
    @Volatile
    private var counter = 0L

    fun nextId(prefix: String = "id"): String = synchronized(this) {
        "${prefix}_${counter++}"
    }
}
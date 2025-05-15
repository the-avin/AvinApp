package com.avin.avinapp.data.utils

import java.io.InputStream

abstract class FileLoader {
    fun loadResource(resourcePath: String): InputStream {
        val stream = this::class.java.classLoader.getResourceAsStream(resourcePath)
        if (stream != null) {
            return stream
        } else throw IllegalStateException("(${resourcePath}) File not found")
    }
}
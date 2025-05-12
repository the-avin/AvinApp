package com.avin.avinapp.data.repository.widget

import com.avin.avinapp.data.models.descriptor.composable.ComposableDescriptor
import kotlinx.serialization.json.Json

class ComposableRepositoryImpl(
    private val json: Json
) : ComposableRepository {
    override fun getAllComposableDescriptors(): List<ComposableDescriptor> {
        val resourcePath = "json/components.json"
        val stream = this::class.java.classLoader.getResourceAsStream(resourcePath)
        if (stream != null) {
            return json.decodeFromString(stream.readBytes().decodeToString())
        } else throw IllegalStateException("Components file not found")
    }
}
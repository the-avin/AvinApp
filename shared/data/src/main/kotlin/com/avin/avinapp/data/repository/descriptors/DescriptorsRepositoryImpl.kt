package com.avin.avinapp.data.repository.descriptors

import com.avin.avinapp.data.models.descriptor.composable.ComposableDescriptor
import com.avin.avinapp.data.models.descriptor.modifier.ModifierDescriptor
import com.avin.avinapp.data.utils.FileLoader
import kotlinx.serialization.json.Json

class DescriptorsRepositoryImpl(
    private val json: Json
) : DescriptorsRepository, FileLoader() {
    override fun getAllComposableDescriptors(): List<ComposableDescriptor> {
        val stream = loadResource("json/components.json")
        return json.decodeFromString(stream.readBytes().decodeToString())
    }

    override fun getAllModifiersDescriptors(): List<ModifierDescriptor> {
        val stream = loadResource("json/modifiers.json")
        return json.decodeFromString(stream.readBytes().decodeToString())
    }
}
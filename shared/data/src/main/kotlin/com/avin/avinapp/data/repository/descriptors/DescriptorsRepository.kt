package com.avin.avinapp.data.repository.descriptors

import com.avin.avinapp.data.models.descriptor.composable.ComposableDescriptor
import com.avin.avinapp.data.models.descriptor.modifier.ModifierDescriptor

interface DescriptorsRepository {
    fun getAllComposableDescriptors(): List<ComposableDescriptor>
    fun getAllModifiersDescriptors(): List<ModifierDescriptor>
}
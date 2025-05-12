package com.avin.avinapp.data.repository.widget

import com.avin.avinapp.data.models.descriptor.composable.ComposableDescriptor

interface ComposableRepository {
    fun getAllComposableDescriptors(): List<ComposableDescriptor>
}
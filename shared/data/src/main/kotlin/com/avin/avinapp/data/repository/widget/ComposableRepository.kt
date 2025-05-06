package com.avin.avinapp.data.repository.widget

import com.avin.avinapp.data.models.widget.ComposableDescriptor

interface ComposableRepository {
    fun getAllComposableDescriptors(): List<ComposableDescriptor>
}
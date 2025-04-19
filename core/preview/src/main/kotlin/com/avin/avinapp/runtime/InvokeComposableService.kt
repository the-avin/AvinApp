package com.avin.avinapp.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import com.avin.avinapp.data.models.widget.ComposableDescriptor

interface InvokeComposableService {
    @Composable
    fun invoke(id: String, composer: Composer, descriptor: ComposableDescriptor)

    @Composable
    fun invokeCaching(id: String, composer: Composer, descriptor: ComposableDescriptor)
}
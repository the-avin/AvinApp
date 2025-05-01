package com.avin.avinapp.preview.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import com.avin.avinapp.data.models.widget.ComposableDescriptorReflection

interface InvokeComposableService {
    @Composable
    fun invoke(id: String, composer: Composer, descriptor: ComposableDescriptorReflection)

    @Composable
    fun invokeCaching(id: String, composer: Composer, descriptor: ComposableDescriptorReflection)
}
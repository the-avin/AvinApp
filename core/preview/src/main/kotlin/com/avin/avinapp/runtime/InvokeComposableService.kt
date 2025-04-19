package com.avin.avinapp.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer
import com.avin.avinapp.data.models.widget.ComposableDescriptor

interface InvokeComposableService {
    @Composable
    fun invoke(composer: Composer, descriptor: ComposableDescriptor)
}
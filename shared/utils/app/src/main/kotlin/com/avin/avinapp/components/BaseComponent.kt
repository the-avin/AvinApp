package com.avin.avinapp.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseComponent(componentContext: ComponentContext) : ComponentContext by componentContext {
    val scope = CoroutineScope(
        Job() + Dispatchers.IO
    )

    init {
        componentContext.lifecycle.doOnDestroy {
            scope.cancel()
        }
    }
}
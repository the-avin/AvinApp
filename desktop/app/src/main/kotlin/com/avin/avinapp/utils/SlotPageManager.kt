package com.avin.avinapp.utils

import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss

class SlotPageManager<T : Any> {
    val navigation = SlotNavigation<T>()

    fun open(config: T) = navigation.activate(config)
    fun close() = navigation.dismiss()
}
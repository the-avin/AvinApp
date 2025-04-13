package com.avin.avinapp.device

import kotlinx.serialization.Serializable

@Serializable
data class PreviewDevice(
    val name: String,
    val resolution: Resolution,
    val type: DeviceType,
    val density: Float
) {
    @Serializable
    data class Resolution(
        val width: Int,
        val height: Int
    )
}

@Serializable
enum class DeviceType {
    MOBILE, TABLET
}
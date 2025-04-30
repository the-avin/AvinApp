package com.avin.avinapp.data.models.device

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntSize
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
        val width: Float,
        val height: Float
    )
}

@Serializable
enum class DeviceType {
    MOBILE, TABLET
}


val PreviewDevice.Resolution.size: Size
    get() = Size(width, height)

val PreviewDevice.Resolution.intSize: IntSize
    get() = IntSize(width.toInt(), height.toInt())
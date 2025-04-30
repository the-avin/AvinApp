package com.avin.avinapp.preview.realtime.utils

internal fun calculateFinalDensity(
    initialDensity: Float,
    frameHeightPx: Float,
    deviceHeight: Float
): Float {
    return initialDensity * (frameHeightPx / deviceHeight)
}

internal fun calculateScaledWidth(
    frameHeightPx: Float,
    aspectRatio: Float
): Float {
    return frameHeightPx * aspectRatio
}
package com.avin.avinapp.data.repository.device

import com.avin.avinapp.device.PreviewDevice
import kotlinx.serialization.json.Json

class DeviceRepositoryImpl(
    private val json: Json
) : DevicesRepository {
    override fun getAllDevices(): List<PreviewDevice> {
        val resourcePath = "json/devices.json"
        val stream = this::class.java.classLoader.getResourceAsStream(resourcePath)
        if (stream != null) {
            return json.decodeFromString(stream.readBytes().decodeToString())
        } else throw IllegalStateException("Devices file not found")
    }
}
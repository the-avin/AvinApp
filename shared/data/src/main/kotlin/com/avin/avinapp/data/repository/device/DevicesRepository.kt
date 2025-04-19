package com.avin.avinapp.data.repository.device

import com.avin.avinapp.data.models.device.PreviewDevice

interface DevicesRepository {
    fun getAllDevices(): List<PreviewDevice>
}
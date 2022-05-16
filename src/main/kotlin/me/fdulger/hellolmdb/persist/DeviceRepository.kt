package me.fdulger.hellolmdb.persist

import me.fdulger.hellolmdb.model.Device
import org.springframework.stereotype.Component

interface DeviceRepository {

    fun findAll(): List<Device>

    fun findById(id: Int): Device

    fun insert(device: Device)

    fun delete(id: Int)
}
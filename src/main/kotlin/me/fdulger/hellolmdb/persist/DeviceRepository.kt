package me.fdulger.hellolmdb.persist

import me.fdulger.hellolmdb.Device

interface DeviceRepository {

    fun findAll(): List<Device>

    fun findById(id: Int): Device

    fun insert(device: Device)

    fun delete(id: Int)
}
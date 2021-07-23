package me.fdulger.hellolmdb.rest

import me.fdulger.hellolmdb.Device
import me.fdulger.hellolmdb.persist.DeviceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

@RestController
class DeviceController {

    @Qualifier("jpa")
    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    @GetMapping(path = ["/devices"])
    fun findAll(): List<Device> {
        return deviceRepository.findAll()
    }

    @GetMapping(path = ["/device"])
    fun find(@RequestParam("id") id: Int): Device {
        return deviceRepository.findById(id)
    }

    @PostMapping(path = ["/device"])
    fun insert(@RequestBody device: Device) {
        deviceRepository.insert(device)
    }

    @DeleteMapping(path = ["/device"])
    fun delete(@RequestParam("id") id: Int) {
        deviceRepository.delete(id)
    }
}
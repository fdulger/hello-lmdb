package me.fdulger.hellolmdb.rest

import me.fdulger.hellolmdb.model.Device
import me.fdulger.hellolmdb.persist.DeviceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

@RestController
class FindDeviceController {

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    @GetMapping(path = ["/device"])
    fun find(@RequestParam("id") id: Int): Response {
        return Response(deviceRepository.findById(id))
    }

    data class Response(val device: Device)
}
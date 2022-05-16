package me.fdulger.hellolmdb.rest

import me.fdulger.hellolmdb.model.Device
import me.fdulger.hellolmdb.persist.DeviceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

@RestController
class FindAllDevicesController {

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    @GetMapping(path = ["/devices"])
    fun findAll(): Response {
        return Response(deviceRepository.findAll())
    }

    data class Response(
        val devices: List<Device>
    )
}
package me.fdulger.hellolmdb.rest

import me.fdulger.hellolmdb.model.Device
import me.fdulger.hellolmdb.persist.DeviceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

@RestController
class InsertDeviceRestController {

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    @PostMapping(path = ["/device"])
    fun insert(@RequestBody request: Request) {
        deviceRepository.insert(request.device)
    }

    data class Request(val device: Device)
}
package me.fdulger.hellolmdb.rest

import me.fdulger.hellolmdb.persist.DeviceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class DeleteDeviceController {

    @Autowired
    private lateinit var deviceRepository: DeviceRepository

    @DeleteMapping(path = ["/device"])
    fun delete(@RequestParam("id") request: Request) {
        deviceRepository.delete(request.id)
    }

    data class Request(val id: Int)
}
package me.fdulger.hellolmdb

import me.fdulger.hellolmdb.model.Device
import me.fdulger.hellolmdb.model.Service
import me.fdulger.hellolmdb.model.ServiceType
import me.fdulger.hellolmdb.rest.FindAllDevicesController
import me.fdulger.hellolmdb.rest.InsertDeviceRestController
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import java.time.Duration
import java.time.Instant

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["app.jpa.enabled=true"]
)
class ApplicationTestBase {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    fun test() {
        val start = Instant.now()
        val baseUrl = "http://localhost:$port"
        val count = 1000
        (1..count).forEach { i -> insertRandomDevice(baseUrl, i) }
        val devices = restTemplate.getForObject("$baseUrl/devices", FindAllDevicesController.Response::class.java)
        assertEquals(devices.devices.size, count)
        val duration = Duration.between(start, Instant.now())
        println("Took: ${duration.toMillis()}ms")
    }

    private fun insertRandomDevice(baseUrl: String, i: Int) {
        val device = Device(
            id = i,
            name = "dev$i",
            version = "v1",
            services = listOf(Service(ServiceType.values()[i % 2], url = "https://192.168.1.$i:8443/"))
        )
        restTemplate.postForLocation("$baseUrl/device", InsertDeviceRestController.Request(device = device))
    }

}

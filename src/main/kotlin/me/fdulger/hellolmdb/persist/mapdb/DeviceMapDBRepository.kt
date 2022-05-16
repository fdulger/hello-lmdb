package me.fdulger.hellolmdb.persist.mapdb

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import me.fdulger.hellolmdb.model.Device
import me.fdulger.hellolmdb.persist.DeviceRepository
import org.mapdb.DB
import org.mapdb.DBMaker
import org.mapdb.Serializer
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentMap
import javax.annotation.PreDestroy


@Component
@ConditionalOnProperty(
    value= ["app.mapdb.enabled"],
    havingValue = "true",
    matchIfMissing = false
)
class DeviceMapDBRepository : DeviceRepository {

    private val objectMapper = jacksonObjectMapper()

    private val db = initDB()
    private val map = initMap(db)

    private fun initDB(): DB {
        return DBMaker.fileDB("mapdb/file.db").fileMmapEnable().make()
    }

    private fun initMap(db: DB): ConcurrentMap<Int, String> {
        return db.hashMap("devices", Serializer.INTEGER, Serializer.STRING).createOrOpen()
    }

    @PreDestroy
    fun close() {
        db.close()
    }

    override fun findAll(): List<Device> {
        return map.values.map { deviceString -> deviceString.convert() }
    }

    override fun findById(id: Int): Device {
        return map.getValue(id).convert()
    }

    override fun insert(device: Device) {
        map[device.id] = device.convert()
    }

    override fun delete(id: Int) {
        map.remove(id)
    }

    private fun String.convert(): Device {
        return objectMapper.readValue(this, Device::class.java)
    }

    private fun Device.convert(): String {
        return objectMapper.writeValueAsString(this)
    }
}
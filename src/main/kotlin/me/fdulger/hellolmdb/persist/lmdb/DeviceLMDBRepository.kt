package me.fdulger.hellolmdb.persist.lmdb

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import me.fdulger.hellolmdb.Device
import me.fdulger.hellolmdb.persist.DeviceRepository
import org.lmdbjava.Dbi
import org.lmdbjava.DbiFlags
import org.lmdbjava.Env
import org.lmdbjava.KeyRange
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import javax.annotation.PreDestroy

@Component
@Qualifier("lmdb")
class DeviceLMDBRepository : DeviceRepository {

    private val DB_NAME = "deviceDB"
    private val objectMapper = jacksonObjectMapper()

    private val env: Env<ByteBuffer>
    private val db: Dbi<ByteBuffer>

    init {
        val file = Path.of("database").toFile()
        if(!file.exists()) file.mkdir()
        env = Env.create()
            .setMapSize(10485760)
            .setMaxDbs(1)
            .open(file)
        db = env.openDbi(DB_NAME, DbiFlags.MDB_CREATE)
    }

    @PreDestroy
    fun close() {
        env.close()
    }

    override fun findAll(): List<Device> {
        env.txnRead().use { txn ->
            db.iterate(txn, KeyRange.all()).use { cursor ->
                return cursor.map {
                    it.`val`().decode().convert()
                }
            }
        }
    }

    override fun findById(id: Int): Device {
        return env.txnRead().use { txn ->
            val found = db[txn, id.asKey()]
            if(found != null) {
                txn.`val`().decode()
            } else {
                throw RuntimeException("No device with id $id")
            }
        }.convert()
    }

    override fun insert(device: Device) {
        db.put(device.id.asKey(), device.asValue())
    }

    override fun delete(id: Int) {
        db.delete(id.asKey())
    }

    private fun Int.asKey(): ByteBuffer {
        val key: ByteBuffer = ByteBuffer.allocateDirect(env.maxKeySize)
        key.put(this.toString().toByteArray()).flip()
        return key
    }

    private fun Device.asValue(): ByteBuffer {
        val deviceJson = this.convert()
        return ByteBuffer.allocateDirect(deviceJson.length)
            .put(deviceJson.toByteArray())
            .flip()
    }

    private fun ByteBuffer.decode(): String {
        return StandardCharsets.UTF_8.decode(this).toString()
    }

    private fun String.convert(): Device {
        return objectMapper.readValue(this, Device::class.java)
    }

    private fun Device.convert(): String {
        return objectMapper.writeValueAsString(this)
    }
}
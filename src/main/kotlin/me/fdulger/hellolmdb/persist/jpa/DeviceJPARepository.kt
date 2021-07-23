package me.fdulger.hellolmdb

import me.fdulger.hellolmdb.persist.DeviceRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.*


@Repository
@Qualifier("jpa")
class DeviceJPARepository(
    private val deviceJPARepositoryInternal: DeviceJPARepositoryInternal
) : DeviceRepository {

    override fun findAll(): List<Device> {
        return deviceJPARepositoryInternal.findAll()
            .map { it.convert() }
    }

    override fun findById(id: Int): Device {
        return deviceJPARepositoryInternal.findById(id.toLong()).orElseThrow {
            RuntimeException("No device with id $id")
        }.convert()
    }

    override fun insert(device: Device) {
        deviceJPARepositoryInternal.save(device.convert())
    }

    override fun delete(id: Int) {
        deviceJPARepositoryInternal.deleteById(id.toLong())
    }

    private fun Device.convert(): DeviceEntity {
        val result = DeviceEntity()
        result.name = name
        result.version = version
        result.services = services.map { it.convert(result) }.toMutableList()
        return result
    }

    private fun Service.convert(device: DeviceEntity): ServiceEntity {
        val result = ServiceEntity()
        result.device = device
        result.type = type
        result.url = url
        return result
    }

    private fun DeviceEntity.convert(): Device {
        return Device(
            id = id.toInt(),
            name = name,
            version = version,
            services = services.map { it.convert() }
        )
    }

    private fun ServiceEntity.convert(): Service {
        return Service(
            type = type,
            url = url,
        )
    }

}


@Entity(name = "Device")
@Table(name = "device")
class DeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    lateinit var name: String
    lateinit var version: String
    @OneToMany(
        mappedBy = "device",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    var services: MutableList<ServiceEntity> = mutableListOf()
}

@Entity
class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne
    lateinit var device: DeviceEntity

    @Enumerated(EnumType.STRING)
    lateinit var type: ServiceType
    lateinit var url: String
}

interface DeviceJPARepositoryInternal: JpaRepository<DeviceEntity, Long>
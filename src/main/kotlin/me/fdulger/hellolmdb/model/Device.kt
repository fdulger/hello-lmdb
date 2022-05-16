package me.fdulger.hellolmdb.model

data class Device(
    val id: Int,
    val name: String,
    val version: String,
    val services: List<Service>
)

data class Service(
    val type: ServiceType,
    val url: String,
)

enum class ServiceType {
    ENCODER,
    DECODER
}
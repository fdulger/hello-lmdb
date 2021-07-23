package me.fdulger.hellolmdb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloLmdbApplication

fun main(args: Array<String>) {
    runApplication<HelloLmdbApplication>(*args)
}

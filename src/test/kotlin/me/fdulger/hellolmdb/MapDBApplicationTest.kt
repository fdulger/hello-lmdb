package me.fdulger.hellolmdb

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["app.mapdb.enabled=true"]
)
class MapDBApplicationTest : ApplicationTestBase() {

    @Test
    fun mapDBDurationTest() {
        test()
    }
}

package me.fdulger.hellolmdb

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["app.lmdb.enabled=true"]
)
class LmdbApplicationTest : ApplicationTestBase() {

    @Test
    fun lmdbDurationTest() {
        test()
    }
}

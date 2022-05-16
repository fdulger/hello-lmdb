package me.fdulger.hellolmdb

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = ["app.jpa.enabled=true"]
)
class JpaApplicationTests : ApplicationTestBase() {

    @Test
    fun jpaDurationTest() {
        test()
    }
}

package com.task.ably.util

import com.task.ably.config.cache.MemberJoinAuthenticationCache
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var cleaner: JpaDatabaseCleaner

    @BeforeEach
    fun beforeEach() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port
        }
        cleaner.truncate()
    }

}
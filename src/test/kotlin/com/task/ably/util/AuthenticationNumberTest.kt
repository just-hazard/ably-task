package com.task.ably.util

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldHaveLength
import org.junit.jupiter.api.Assertions.*

class AuthenticationNumberTest : BehaviorSpec ({
    given("인증 번호 생성 오브젝트") {
        `when`("인증 번호 생성") {
            val result = AuthenticationNumber.generateAuthenticationNumber()
            then("검증") {
                result shouldNotBe null
                result shouldHaveLength 4
            }
        }
    }
})
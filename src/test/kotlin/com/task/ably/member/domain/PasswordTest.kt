package com.task.ably.member.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe

class PasswordTest : BehaviorSpec ({
    given("비밀번호 암호화") {
        val password = "ably1234"
        `when`("비밀번호 입력") {
            val result = Password(password).value

            then("암호화 확인") {
                result shouldNotBe password
            }
        }
    }
})
package com.task.ably.member.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*

class LoginTypeTest : BehaviorSpec ({
    given("로그인 타입 체크") {
        `when`("이메일") {
            val result = LoginType.confirmType("ably@gmail.com")
            then("검증") {
                result shouldBe LoginType.EMAIL
            }
        }

        `when`("핸드폰") {
            val result = LoginType.confirmType("010-0000-0000")
            then("검증") {
                result shouldBe LoginType.PHONE
            }
        }

        `when`("닉네임") {
            val result = LoginType.confirmType("에이블리")
            then("검증") {
                result shouldBe LoginType.NICKNAME
            }
        }
    }
})
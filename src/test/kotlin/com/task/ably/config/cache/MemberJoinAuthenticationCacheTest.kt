package com.task.ably.config.cache

import com.task.ably.exception.InvalidAuthenticationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldHave
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldHaveLength
import org.junit.jupiter.api.Assertions.*

class MemberJoinAuthenticationCacheTest : BehaviorSpec({
    given("회원 인증 캐시") {
        val phoneNumber = "010-0000-0000"
        `when`("인증번호 생성") {
            val result = MemberJoinAuthenticationCache.generateVerificationNumber(phoneNumber)
            then("생성 번호 검증") {
                result.authenticationNumber shouldNotBe null
                result.authenticationNumber shouldHaveLength 4
            }
        }
    }

    given("인증 번호 확인") {
        val phoneNumber = "010-0000-0001"
        val result = MemberJoinAuthenticationCache.generateVerificationNumber(phoneNumber)

        `when`("검증") {
            MemberJoinAuthenticationCache.confirmVerificationNumber(phoneNumber, result.authenticationNumber)
            then("확인") {
                MemberJoinAuthenticationCache.authenticationConfirmMap[phoneNumber] shouldBe true
                MemberJoinAuthenticationCache.authenticationMap[phoneNumber] shouldBe null
            }
        }

        `when`("잘못된 인증번호") {
            then("error") {
                shouldThrow<InvalidAuthenticationException> {
                    MemberJoinAuthenticationCache.confirmVerificationNumber(phoneNumber, "00000")
                }
            }
        }
    }
})
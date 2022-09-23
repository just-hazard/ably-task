package com.task.ably.member.dto

import com.task.ably.config.message.ErrorMessage
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class VerificationDtoTest : BehaviorSpec({
    given("create validator object") {
        val validator: Validator = Validation.buildDefaultValidatorFactory().validator

        `when`("핸드폰 정규식이 아닐 경우 (문자열)") {
            val violations = validator.validate(
                PhoneNumberRequest(
                    "문자열"
                )
            )

            then("error message") {
                violations.first().message shouldBe ErrorMessage.PHONE_NUMBER_ERROR
            }
        }

        `when`("핸드폰 정규식이 아닐 경우 (잘못된 핸드폰 번호)") {
            val violations = validator.validate(
                PhoneNumberRequest(
                    "010-0000--1111"
                )
            )

            then("error message") {
                violations.first().message shouldBe ErrorMessage.PHONE_NUMBER_ERROR
            }
        }

        `when`("인증번호가 4자리가 아닐 경우") {
            val violations = validator.validate(
                AuthenticationNumberRequest(
                    "010-0000-1111",
                    "123"
                )
            )

            then("error message") {
                violations.first().message shouldBe "크기가 4에서 4 사이여야 합니다"
            }
        }
    }
})
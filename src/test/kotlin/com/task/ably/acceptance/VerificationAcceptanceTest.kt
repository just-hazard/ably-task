package com.task.ably.acceptance

import com.sun.istack.NotNull
import com.task.ably.member.dto.*
import com.task.ably.util.AcceptanceTest
import io.kotest.matchers.shouldBe
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class VerificationAcceptanceTest : AcceptanceTest() {

    @Test
    fun `인증번호 요청 확인`() {
        `인증번호 요청`(
            PhoneNumberRequest(
                "010-0000-0000"
            )
        ) Then {
            statusCode(HttpStatus.OK.value())
            body("authenticationNumber", notNullValue())
        }
    }

    @Test
    fun `인증번호 요청 검증`() {
        var authenticationNumber = ""

        `인증번호 요청`(
            PhoneNumberRequest(
                "010-0000-0000"
            )
        ) Extract {
            authenticationNumber = this.jsonPath().getString("authenticationNumber")
        }

        `인증번호 검증`(
            AuthenticationNumberRequest(
                "010-0000-0000",
                authenticationNumber
            )
        ) Then {
            statusCode(HttpStatus.NO_CONTENT.value())
        }
    }

    @Test
    fun `잘못된 인증번호 기입`() {

        `인증번호 요청`(
            PhoneNumberRequest(
                "010-0000-0000"
            )
        )

        `인증번호 검증`(
            AuthenticationNumberRequest(
                "010-0000-0000",
                "1111"
            )
        ) Then {
            statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        }
    }

    @Test
    fun `인증번호 2번 요청`() {
        `인증번호 요청`(
            PhoneNumberRequest(
                "010-0000-0000"
            )
        )

        `인증번호 요청`(
            PhoneNumberRequest(
                "010-0000-0000"
            )
        ) Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            println(body().asPrettyString())
        }
    }

    @Test
    fun `회원 비밀번호 재설정`() {
        var authenticationNumber = ""

        MemberAcceptanceTest.회원가입(
            CreateMemberRequest(
                "abc@gmail.com",
                "에이블리",
                "ably1234",
                "홍길동",
                "010-0000-0000"
            )
        )
        `회원 확인 후 인증번호 요청`(
            PhoneNumberRequest(
                "010-0000-0000"
            )
        ) Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            println(body().asPrettyString())
            authenticationNumber = this.jsonPath().getString("authenticationNumber")
        }

        `인증번호 검증`(
            AuthenticationNumberRequest(
                "010-0000-0000",
                authenticationNumber
            )
        ) Then {
            statusCode(HttpStatus.NO_CONTENT.value())
        }

        MemberAcceptanceTest.`비밀번호 변경`(
            PatchPasswordRequest(
                "010-0000-0000",
                "change1234"
            )
        ) Then {
            statusCode(HttpStatus.NO_CONTENT.value())
        } Extract {
            println(body().asPrettyString())
        }
    }

    companion object {
        fun `회원 확인 후 인증번호 요청`(request: PhoneNumberRequest): Response {
            return Given {
                contentType(ContentType.JSON)
                body(request)
            } When {
                post("apis/phone/authentication/members")
            }
        }

        fun `인증번호 요청`(request: PhoneNumberRequest): Response {
            return Given {
                contentType(ContentType.JSON)
                body(request)
            } When {
                post("apis/phone/authentication")
            }
        }

        fun `인증번호 검증`(request: AuthenticationNumberRequest): Response {
            return Given {
                contentType(ContentType.JSON)
                body(request)
            } When {
                put("apis/phone/authentication")
            }
        }
    }
}
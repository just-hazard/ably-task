package com.task.ably.acceptance

import com.task.ably.member.dto.*
import com.task.ably.util.AcceptanceTest
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.apache.http.HttpHeaders.AUTHORIZATION
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

class MemberAcceptanceTest : AcceptanceTest() {

    @BeforeEach
    fun setUp() {
        var authenticationNumber = ""

        VerificationAcceptanceTest.`인증번호 요청`(
            PhoneNumberRequest(
                "010-0000-0000"
            )
        ) Extract {
            authenticationNumber = this.jsonPath().getString("authenticationNumber")
        }

        VerificationAcceptanceTest.`인증번호 검증`(
            AuthenticationNumberRequest(
                "010-0000-0000",
                authenticationNumber
            )
        )
    }

    @Test
    fun `회원가입 확인`() {
        `회원가입`(
            CreateMemberRequest(
                "ably@gmail.com",
                "에이블리",
                "ably1234",
                "홍길동",
                "010-0000-0000"
            )
        ) Then {
            statusCode(HttpStatus.CREATED.value())
            header("Location", equalTo("apis/members/1"))
        }
    }

    @Test
    fun `인증 되지 않은 핸드폰으로 가입 시`() {
        `회원가입`(
            CreateMemberRequest(
                "ably@gmail.com",
                "에이블리",
                "ably1234",
                "홍길동",
                "010-0000-0000"
            )
        ) Then {
            statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        }
    }

    @Test
    fun `로그인 확인 (이메일)`() {
        `회원가입`(
            CreateMemberRequest(
                "ably@gmail.com",
                "에이블리",
                "ably1234",
                "홍길동",
                "010-0000-0000"
            )
        )

        `로그인`(
            LoginRequest(
                "ably@gmail.com",
                "ably1234",
            )
        ) Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            println(body().asPrettyString())
        }
    }

    @Test
    fun `로그인 확인 (핸드폰 번호)`() {
        `회원가입`(
            CreateMemberRequest(
                "ably@gmail.com",
                "에이블리",
                "ably1234",
                "홍길동",
                "010-0000-0000"
            )
        )

        `로그인`(
            LoginRequest(
                "010-0000-0000",
                "ably1234",
            )
        ) Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            println(body().asPrettyString())
        }
    }

    @Test
    fun `로그인 확인 (닉네임)`() {
        `회원가입`(
            CreateMemberRequest(
                "ably@gmail.com",
                "에이블리",
                "ably1234",
                "홍길동",
                "010-0000-0000"
            )
        )

        `로그인`(
            LoginRequest(
                "에이블리",
                "ably1234",
            )
        ) Then {
            statusCode(HttpStatus.OK.value())
        } Extract {
            println(body().asPrettyString())
        }
    }

    @Test
    fun `비밀번호 틀렸을 경우`() {
        `회원가입`(
            CreateMemberRequest(
                "ably@gmail.com",
                "에이블리",
                "ably1234",
                "홍길동",
                "010-0000-0000"
            )
        )

        `로그인`(
            LoginRequest(
                "ably@gmail.com",
                "ably123411",
            )
        ) Then {
            statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        }
    }

    @Test
    fun `내 정보 확인`() {
        `회원가입`(
            CreateMemberRequest(
                "ably@gmail.com",
                "에이블리",
                "ably1234",
                "홍길동",
                "010-0000-0000"
            )
        )

        var token = ""

        `로그인`(
            LoginRequest(
                "ably@gmail.com",
                "ably1234",
            )
        ) Extract {
            token = body().asPrettyString()
        }

        `내 정보 보기`(
            token
        ) Then {
            statusCode(HttpStatus.OK.value())
        } Extract  {
            println(body().asPrettyString())
        }
    }

    @Test
    fun `유효한 토큰이 아닐 경우`() {
        `내 정보 보기`(
            "1234"
        ) Then {
            statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        }
    }

    @Test
    fun `회원이 아닌 전화번호로 인증번호 요청`() {
        VerificationAcceptanceTest.`회원 확인 후 인증번호 요청`(
            PhoneNumberRequest(
                "010-0000-0000"
            )
        ) Then {
            statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        }
    }

    @Test
    fun `비밀번호 변경 확인`() {
        var authenticationNumber = ""

        `회원가입`(
            CreateMemberRequest(
                "ably@gmail.com",
                "에이블리",
                "ably1234",
                "홍길동",
                "010-0000-0000"
            )
        )

        VerificationAcceptanceTest.`회원 확인 후 인증번호 요청`(
            PhoneNumberRequest(
                "010-0000-0000"
            )
        ) Extract {
            authenticationNumber = this.jsonPath().getString("authenticationNumber")
        }

        VerificationAcceptanceTest.`인증번호 검증`(
            AuthenticationNumberRequest(
                "010-0000-0000",
                authenticationNumber
            )
        )

        `비밀번호 변경`(
            PatchPasswordRequest(
            "010-0000-0000",
            "passwordChanged"
            )
        ) Then {
            statusCode(HttpStatus.NO_CONTENT.value())
        }

        `로그인`(
            LoginRequest(
                "ably@gmail.com",
                "passwordChanged",
            )
        ) Then {
            statusCode(HttpStatus.OK.value())
        }
    }

    companion object {
        fun 회원가입(request: CreateMemberRequest): Response {
            return Given {
                contentType(ContentType.JSON)
                body(request)
            } When {
                post("apis/members")
            }
        }

        fun 로그인(request: LoginRequest): Response {
            return Given {
                contentType(ContentType.JSON)
                body(request)
            } When {
                post("apis/login")
            }
        }

        fun `내 정보 보기`(token: String): Response {
            return Given {
                contentType(ContentType.JSON)
                header(AUTHORIZATION, "Bearer $token")
            } When {
                get("apis/members/1")
            }
        }

        fun `비밀번호 변경`(request: PatchPasswordRequest): Response {
            return Given {
                contentType(ContentType.JSON)
                body(request)
            } When {
                patch("apis/members")
            }
        }
    }
}
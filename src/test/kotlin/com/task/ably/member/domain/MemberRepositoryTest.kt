package com.task.ably.member.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class MemberRepositoryTest{

    @Autowired
    private lateinit var repository: MemberRepository

    @BeforeEach
    fun setUp() {
        repository.save(
            Member(
                "ably@gmail.com",
                "에이블리",
                Password("ably1234"),
                "홍길동",
                "010-0000-0000"
            )
        )
    }

    @Test
    fun `이메일 조회`() {
        val actual = repository.findByEmail("ably@gmail.com")
        assertThat(actual).isNotNull
        assertThat(actual!!.email).isEqualTo("ably@gmail.com")
    }

    @Test
    fun `핸드폰 조회`() {
        val actual = repository.findByPhone("010-0000-0000")
        assertThat(actual).isNotNull
        assertThat(actual!!.phone).isEqualTo("010-0000-0000")
    }

    @Test
    fun `닉네임 조회`() {
        val actual = repository.findByNickname("에이블리")
        assertThat(actual).isNotNull
        assertThat(actual!!.nickname).isEqualTo("에이블리")
    }
}
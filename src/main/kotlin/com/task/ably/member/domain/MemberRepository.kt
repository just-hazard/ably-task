package com.task.ably.member.domain

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
    fun findByPhone(phoneNumber: String): Member?
    fun findByNickname(nickname: String): Member?
}
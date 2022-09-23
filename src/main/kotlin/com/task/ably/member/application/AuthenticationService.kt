package com.task.ably.member.application

import com.task.ably.config.cache.MemberJoinAuthenticationCache
import com.task.ably.member.dto.AuthenticationNumberResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthenticationService(
    private val memberService: MemberService
) {

    fun generateAuthenticationNumber(phoneNumber: String): AuthenticationNumberResponse {
        return MemberJoinAuthenticationCache.generateVerificationNumber(phoneNumber)
    }

    fun confirmAuthenticationNumber(phoneNumber: String, authenticationNumber: String) {
        MemberJoinAuthenticationCache.confirmVerificationNumber(phoneNumber, authenticationNumber)
    }

    fun generateMemberAuthenticationNumber(phoneNumber: String): AuthenticationNumberResponse {
        memberService.findMember(phoneNumber)
        return MemberJoinAuthenticationCache.generateVerificationNumber(phoneNumber)
    }
}
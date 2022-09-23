package com.task.ably.member.application

import com.task.ably.config.cache.MemberJoinAuthenticationCache
import com.task.ably.config.security.JwtFilter
import com.task.ably.exception.IdNotFoundException
import com.task.ably.member.domain.LoginType
import com.task.ably.member.domain.Member
import com.task.ably.member.domain.MemberRepository
import com.task.ably.member.domain.Password
import com.task.ably.member.dto.GetMemberResponse
import com.task.ably.member.dto.LoginRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional
class MemberService(
    private val repository: MemberRepository,
    private val jwtFilter: JwtFilter
) {
    fun create(email: String, nickname: String, password: String, name: String, phone: String): Long {
        MemberJoinAuthenticationCache.confirmAuthenticationPhoneNumber(phone)

        return repository.save(
            Member(
                email = email,
                nickname = nickname,
                password = Password(password),
                name = name,
                phone = phone
            )
        ).id!!
    }

    @Transactional(readOnly = true)
    fun login(request: LoginRequest): String {

        val member = LoginType.confirmType(request.id).run {
            if(this == LoginType.EMAIL) {
                repository.findByEmail(request.id)
            } else if(this == LoginType.PHONE) {
                repository.findByPhone(request.id)
            } else {
                repository.findByNickname(request.id)
            }
        }

        member?.also {
            it.isSamePassword(request.password)
        } ?: throw IdNotFoundException()

        return jwtFilter.createToken(member.email)
    }

    @Transactional(readOnly = true)
    fun getInformation(id: Long): GetMemberResponse {
        val member = repository.findById(id).orElseThrow { throw EntityNotFoundException() }
        return GetMemberResponse.of(member)
    }

    @Transactional(readOnly = true)
    fun findMember(phoneNumber: String) {
        findByPhone(phoneNumber)
    }

    fun changePassword(phone: String, password: String) {
        val entity = findByPhone(phone)
        entity.changePassword(password)
    }

    private fun findByPhone(phone: String) = repository.findByPhone(phone) ?: throw EntityNotFoundException()
}
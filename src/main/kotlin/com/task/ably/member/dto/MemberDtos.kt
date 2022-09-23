package com.task.ably.member.dto

import com.task.ably.config.message.ErrorMessage
import com.task.ably.member.domain.Member
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class CreateMemberRequest(
    @field:Email
    val email: String,
    @field:NotBlank
    val nickname: String,
    @field:NotBlank
    val password: String,
    @field:NotBlank
    val name: String,
    @field:Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = ErrorMessage.PHONE_NUMBER_ERROR)
    val phone: String
)

data class LoginRequest(
    @field:NotBlank
    val id: String,
    @field:NotBlank
    val password: String,
)

data class GetMemberResponse(
    val email: String,
    val nickname: String,
    val password: String,
    val name: String,
    val phone: String
) {
    companion object {
        fun of(member: Member): GetMemberResponse {
            return GetMemberResponse(
                member.email,
                member.nickname,
                member.password.value,
                member.name,
                member.phone
            )
        }
    }
}

data class PatchPasswordRequest(
    @field:Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = ErrorMessage.PHONE_NUMBER_ERROR)
    val phone: String,
    @field:NotBlank
    val password: String
)
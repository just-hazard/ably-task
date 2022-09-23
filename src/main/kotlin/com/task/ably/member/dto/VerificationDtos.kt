package com.task.ably.member.dto

import com.task.ably.config.message.ErrorMessage
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class PhoneNumberRequest(
    @field:Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = ErrorMessage.PHONE_NUMBER_ERROR)
    val phoneNumber: String
)

data class AuthenticationNumberResponse(
    val phoneNumber: String,
    val authenticationNumber: String
)

data class AuthenticationNumberRequest(
    @field:Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = ErrorMessage.PHONE_NUMBER_ERROR)
    val phoneNumber: String,
    @field:Size(min = 4, max = 4)
    val authenticationNumber: String
)
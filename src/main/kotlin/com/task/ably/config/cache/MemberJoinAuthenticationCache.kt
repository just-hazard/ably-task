package com.task.ably.config.cache

import com.task.ably.exception.InvalidAuthenticationException
import com.task.ably.exception.UnauthenticatedPhoneException
import com.task.ably.member.dto.AuthenticationNumberResponse
import com.task.ably.util.AuthenticationNumber


object MemberJoinAuthenticationCache {

    var authenticationMap: MutableMap<String, String> = mutableMapOf()
    var authenticationConfirmMap: MutableMap<String, Boolean> = mutableMapOf()

    fun generateVerificationNumber(phoneNumber: String): AuthenticationNumberResponse {
        authenticationMap[phoneNumber] = AuthenticationNumber.generateAuthenticationNumber()

        return AuthenticationNumberResponse(
            phoneNumber,
            authenticationMap[phoneNumber]!!
        )
    }

    fun confirmVerificationNumber(phoneNumber: String, authenticationNumber: String) {
        if(authenticationMap.containsKey(phoneNumber) && authenticationMap[phoneNumber] == authenticationNumber) {
            authenticationConfirmMap[phoneNumber] = true
            deleteVerificationNumber(phoneNumber)
            return
        }
        throw InvalidAuthenticationException()
    }

    private fun deleteVerificationNumber(phoneNumber: String) {
        authenticationMap.remove(phoneNumber)
    }

    fun confirmAuthenticationPhoneNumber(phone: String) {
        if(authenticationConfirmMap[phone] == true) {
            return
        }

        throw UnauthenticatedPhoneException()
    }

    fun clear() {
        authenticationMap.clear()
        authenticationConfirmMap.clear()
    }
}
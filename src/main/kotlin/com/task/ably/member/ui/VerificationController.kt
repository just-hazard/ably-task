package com.task.ably.member.ui

import com.task.ably.member.application.AuthenticationService
import com.task.ably.member.dto.AuthenticationNumberRequest
import com.task.ably.member.dto.AuthenticationNumberResponse
import com.task.ably.member.dto.PhoneNumberRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("apis/phone/authentication")
class VerificationController(
    private val service: AuthenticationService
) {

    @PostMapping
    fun getAuthenticationNumber(@RequestBody @Valid request: PhoneNumberRequest): ResponseEntity<AuthenticationNumberResponse> {
        return ResponseEntity.ok().body(service.generateAuthenticationNumber(request.phoneNumber))
    }

    @PutMapping
    fun confirmAuthenticationNumber(@RequestBody @Valid request: AuthenticationNumberRequest): ResponseEntity<Void> {
        service.confirmAuthenticationNumber(request.phoneNumber, request.authenticationNumber)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/members")
    fun getMemberAuthenticationNumber(@RequestBody @Valid request: PhoneNumberRequest): ResponseEntity<AuthenticationNumberResponse> {
        return ResponseEntity.ok().body(service.generateMemberAuthenticationNumber(request.phoneNumber))
    }
}